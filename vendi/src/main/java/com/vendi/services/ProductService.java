package com.vendi.services;

import com.vendi.domain.product.Product;
import com.vendi.domain.product.ProductRequestDTO;
import com.vendi.domain.user.User;
import com.vendi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    UserAuthenticatedService userAuthenticatedService;

    @Autowired
    ProductRepository repository;

    public Product save(ProductRequestDTO productDTO) {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setQuantity(productDTO.quantity());
        product.setInstallment(productDTO.installment());
        product.setDiscount(productDTO.discount());
        product.setUser(user);

        return repository.save(product);
    }

    public List<Product> getUserProducts() {
        User user = userAuthenticatedService.getAuthenticatedUser();
        return user.getProducts();
    }
}
