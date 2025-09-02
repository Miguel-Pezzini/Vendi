package com.vendi.product.service;

import com.vendi.photo.service.PhotoService;
import com.vendi.product.dto.*;
import com.vendi.product.mapper.ProductMapper;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.photo.model.Photo;
import com.vendi.category.model.Category;
import com.vendi.product.model.Product;
import com.vendi.product.repository.ProductRepository;
import com.vendi.category.service.CategoryService;
import com.vendi.user.service.UserAuthenticatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository repository;
    private final UserAuthenticatedService userAuthenticatedService;
    private final PhotoService photoService;

    @Transactional
    public ProductDTO create(CreateProductDTO createProductDTO) throws ResourceNotFoundException {
        Product product = ProductMapper.createDTOToProduct(createProductDTO);

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(createProductDTO.categoriesIds()));
        ProductMapper.validateCategories(categories, createProductDTO.categoriesIds());
        ProductMapper.validateMainPhoto(createProductDTO.photos());

        product.setUser(this.userAuthenticatedService.getAuthenticatedUser());
        product.setCategories(categories);

        product.addPhotos(photoService.createPhotos(createProductDTO.photos()));

        return ProductMapper.toDTO(this.repository.save(product));
    }

    public List<ProductDTO>getProducts(ProductQueryParams productQueryParams) {
        List<Product> products = this.repository.findAllByCustomFilter(productQueryParams);

        return products.stream().map(ProductMapper::toDTO).toList();
    }

    public ProductDTO getById(UUID productId) throws ResourceNotFoundException {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        return ProductMapper.toDTO(product);
    }

    public ProductDetailsDTO getDetailsById(UUID productId) throws ResourceNotFoundException {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        return ProductMapper.toDetailsDTO(product);
    }

    public List<ProductDTO> getLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return this.repository.findRecentProducts(pageable).stream().map(ProductMapper::toDTO).toList();
    }

    @Transactional
    public ProductDTO update(UUID productId, UpdateProductDTO updateProductDTO) throws ResourceNotFoundException {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(updateProductDTO.categoriesIds()));
        ProductMapper.validateCategories(categories, updateProductDTO.categoriesIds());
        ProductMapper.updateDTOToProduct(updateProductDTO, product);

        product.setCategories(categories);

        return ProductMapper.toDTO(repository.save(product));
    }

    public void delete(UUID productId) {
        repository.deleteById(productId);
    }
}
