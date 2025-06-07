package com.vendi.product.service;

import com.vendi.photo.dto.CreatePhotoRequestDTO;
import com.vendi.photo.mapper.PhotoMapper;
import com.vendi.product.dto.ProductRequestDTO;
import com.vendi.product.dto.ProductResponseDTO;
import com.vendi.product.mapper.ProductMapper;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.photo.model.Photo;
import com.vendi.category.model.Category;
import com.vendi.product.model.Product;
import com.vendi.product.dto.CreateProductRequestDTO;
import com.vendi.product.dto.UpdateProductRequestDTO;
import com.vendi.shared.exception.ValidationExceptions.IllegalArgumentException;
import com.vendi.user.model.User;
import com.vendi.product.repository.ProductRepository;
import com.vendi.category.service.CategoryService;
import com.vendi.user.service.UserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository repository;
    private final UserAuthenticatedService userAuthenticatedService;

    public ProductService(
            CategoryService categoryService,
            ProductRepository repository,
            UserAuthenticatedService userAuthenticatedService
    ) {
        this.categoryService = categoryService;
        this.repository = repository;
        this.userAuthenticatedService = userAuthenticatedService;
    }

    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO createproductDTO) throws ResourceNotFoundException, IllegalArgumentException {
        Product product = ProductMapper.mapToProduct(createproductDTO);

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(createproductDTO.categoriesIds()));
        this.validateCategories(categories, createproductDTO.categoriesIds());

        product.setUser(this.userAuthenticatedService.getAuthenticatedUser());
        product.setCategories(categories);

        List<Photo> photos = PhotoMapper.mapToPhotos(createproductDTO.photos());
        this.validateMainPhoto(photos);
        for (Photo photo : photos) {
            product.addPhoto(photo);
        }

        Product saved = this.repository.save(product);

        return new ProductResponseDTO(saved);
    }

    void validateCategories(Set<Category> categories, List<UUID> categoriesIds) throws ResourceNotFoundException {
        if (categories.size() != categoriesIds.size()) {
            throw new ResourceNotFoundException("Categories not found");
        }
    }

    void validateMainPhoto(List<Photo> photos) throws IllegalArgumentException {
        photos.stream()
                .filter(Photo::getIsMain)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("The product must contain only one main photo."));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO>getProducts(ProductRequestDTO productRequestDTO) {
        List<Product> products = this.repository.findAllByCustomFilter(productRequestDTO);

        return products.stream().map(ProductResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getUserProducts() {
        User user = userAuthenticatedService.getAuthenticatedUser();

        return user.getProducts().stream().map(ProductResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getById(UUID productId) throws ResourceNotFoundException {
        Optional<Product> product = repository.findById(productId);

        if(product.isEmpty()) {
            throw new ResourceNotFoundException("This product does not exists");
        }

        return new ProductResponseDTO(product.get());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return this.repository.findRecentProducts(pageable).stream().map(ProductResponseDTO::new).toList();
    }

    public ProductResponseDTO update(UUID productId, UpdateProductRequestDTO productDTO) {
        Product product = repository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (productDTO.name() != null) product.setName(productDTO.name());
        if (productDTO.price() != null) product.setPrice(productDTO.price());
        if (productDTO.quantity() != null) product.setQuantity(productDTO.quantity());
        if (productDTO.installment() != null) product.setInstallment(productDTO.installment());
        if (productDTO.discount() != null) product.setDiscount(productDTO.discount());

        Product savedProduct = repository.save(product);
        return new ProductResponseDTO(savedProduct);
    }

    public void delete(UUID productId) {
        repository.deleteById(productId);
    }
}
