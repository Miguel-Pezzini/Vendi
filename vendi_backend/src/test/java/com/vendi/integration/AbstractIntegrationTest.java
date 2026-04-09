package com.vendi.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.service.CategoryService;
import com.vendi.infra.security.TokenService;
import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import com.vendi.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TokenService tokenService;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    private static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.execute("""
            DO
            $func$
            BEGIN
                EXECUTE
                (SELECT 'TRUNCATE TABLE ' || string_agg(format('%I.%I', schemaname, tablename), ', ')
                        || ' RESTART IDENTITY CASCADE'
                 FROM pg_tables
                 WHERE schemaname = 'public');
            END
            $func$;
        """);
    }

    protected User createUser(UserRole role) {
        String unique = UUID.randomUUID().toString();
        User user = new User(
                role.name().toLowerCase() + "-" + unique + "@vendi.test",
                passwordEncoder.encode("123456"),
                role.name().toLowerCase() + "-" + unique,
                role
        );

        return userRepository.save(user);
    }

    protected String bearerTokenFor(UserRole role) {
        return "Bearer " + tokenService.generateToken(createUser(role));
    }

    protected String bearerTokenFor(User user) {
        return "Bearer " + tokenService.generateToken(user);
    }

    protected CategoryResponseDTO createCategory(String name) {
        return categoryService.create(new CategoryRequestDTO(name, name + " description", null));
    }

    protected CreateProductDTO createProductDTO(String name, float price, List<UUID> categoryIds) {
        return new CreateProductDTO(
                name,
                price,
                3,
                2,
                10,
                List.of(
                        createPhotoDTO(true, name + "-main.png"),
                        createPhotoDTO(false, name + "-gallery.png")
                ),
                categoryIds
        );
    }

    protected ProductDTO createProductThroughApi(String bearerToken, String name, float price, List<UUID> categoryIds) throws Exception {
        String responseBody = mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/products")
                                .header("Authorization", bearerToken)
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(asJson(createProductDTO(name, price, categoryIds)))
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(responseBody, ProductDTO.class);
    }

    protected String asJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    private CreatePhotoDTO createPhotoDTO(boolean isMainPhoto, String filename) {
        String base64 = Base64.getEncoder()
                .encodeToString(("image-" + filename).getBytes(StandardCharsets.UTF_8));
        return new CreatePhotoDTO(isMainPhoto, base64, "image/png", filename);
    }
}
