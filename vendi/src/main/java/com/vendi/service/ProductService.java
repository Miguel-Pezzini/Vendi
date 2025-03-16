package com.vendi.service;

import com.vendi.dto.photo.CreatePhotoRequestDTO;
import com.vendi.model.photo.Photo;
import com.vendi.model.product.Product;
import com.vendi.dto.product.CreateProductRequestDTO;
import com.vendi.dto.product.UpdateProductRequestDTO;
import com.vendi.model.user.User;
import com.vendi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    UserAuthenticatedService userAuthenticatedService;

    @Autowired
    ProductRepository repository;

    @Autowired
    PhotoService photoService;

    @Transactional
    public Product create(CreateProductRequestDTO createproductDTO) {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Product product = new Product();
        product.setName(createproductDTO.name());
        product.setPrice(createproductDTO.price());
        product.setQuantity(createproductDTO.quantity());
        product.setInstallment(createproductDTO.installment());
        product.setDiscount(createproductDTO.discount());
        product.setUser(user);

        Product savedProduct = repository.save(product);

        createproductDTO.photos().stream().filter(CreatePhotoRequestDTO::isMainPhoto).findFirst().ifPresent(photoDTO -> {
            Photo mainPhoto = this.photoService.createMainPhoto(photoDTO, savedProduct);
            savedProduct.setMainPhoto(mainPhoto);
        });

        repository.save(savedProduct);

        this.photoService.createPhotos(createproductDTO.photos(), savedProduct);

        return savedProduct;
    }

    public List<Product> getUserProducts() {
        User user = userAuthenticatedService.getAuthenticatedUser();

        return user.getProducts();
    }

    public Optional<Product> getById(UUID productId) {
        return repository.findById(productId);
    }

    public Product update(UUID productId, UpdateProductRequestDTO productDTO) {
        Product product = repository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (productDTO.name() != null) product.setName(productDTO.name());
        if (productDTO.price() != null) product.setPrice(productDTO.price());
        if (productDTO.quantity() != null) product.setQuantity(productDTO.quantity());
        if (productDTO.installment() != null) product.setInstallment(productDTO.installment());
        if (productDTO.discount() != null) product.setDiscount(productDTO.discount());

        return repository.save(product);
    }

    public void delete(UUID productId) {
        repository.deleteById(productId);
    }
}
