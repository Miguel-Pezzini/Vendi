package com.vendi.service;

import com.vendi.model.product.Product;
import com.vendi.dto.product.CreateProductRequestDTO;
import com.vendi.dto.product.UpdateProductRequestDTO;
import com.vendi.model.user.User;
import com.vendi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    UserAuthenticatedService userAuthenticatedService;

    @Autowired
    ProductRepository repository;

    public Product create(CreateProductRequestDTO CreateproductDTO) {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Product product = new Product();
        product.setName(CreateproductDTO.name());
        product.setPrice(CreateproductDTO.price());
        product.setQuantity(CreateproductDTO.quantity());
        product.setInstallment(CreateproductDTO.installment());
        product.setDiscount(CreateproductDTO.discount());
        product.setUser(user);

        return repository.save(product);
    }

    public List<Product> getUserProducts() {
        User user = userAuthenticatedService.getAuthenticatedUser();
        return user.getProducts();
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
