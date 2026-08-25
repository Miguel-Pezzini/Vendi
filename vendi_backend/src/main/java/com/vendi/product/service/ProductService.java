package com.vendi.product.service;

import com.vendi.category.model.Category;
import com.vendi.category.service.CategoryService;
import com.vendi.photo.service.PhotoService;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.dto.ProductDetailsDTO;
import com.vendi.product.dto.ProductQueryParams;
import com.vendi.product.dto.UpdateProductDTO;
import com.vendi.product.mapper.ProductMapper;
import com.vendi.product.model.Product;
import com.vendi.product.repository.ProductRepository;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.shared.money.Money;
import com.vendi.user.service.UserAuthenticatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
        product.setPrice(Money.of(createProductDTO.price()));

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(createProductDTO.categoriesIds()));
        ProductMapper.validateCategories(categories, createProductDTO.categoriesIds());
        ProductMapper.validateMainPhoto(createProductDTO.photos());

        product.setUser(this.userAuthenticatedService.getAuthenticatedUser());
        product.setCategories(categories);
        product.addPhotos(photoService.createPhotos(createProductDTO.photos()));

        return ProductMapper.toDTO(this.repository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts(ProductQueryParams productQueryParams) {
        return this.repository.findAll(productQueryParams).stream().map(ProductMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(UUID productId) throws ResourceNotFoundException {
        return ProductMapper.toDTO(findWithCatalog(productId));
    }

    @Transactional(readOnly = true)
    public ProductDetailsDTO getDetailsById(UUID productId) throws ResourceNotFoundException {
        return ProductMapper.toDetailsDTO(findWithCatalog(productId));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, Math.min(Math.max(limit, 1), ProductQueryParams.MAX_SIZE));
        return this.repository.findRecentProducts(pageable).stream().map(ProductMapper::toDTO).toList();
    }

    @Transactional
    public ProductDTO update(UUID productId, UpdateProductDTO updateProductDTO) throws ResourceNotFoundException {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(updateProductDTO.categoriesIds()));
        ProductMapper.validateCategories(categories, updateProductDTO.categoriesIds());
        ProductMapper.updateDTOToProduct(updateProductDTO, product);
        product.setPrice(Money.of(updateProductDTO.price()));
        product.setCategories(categories);

        return ProductMapper.toDTO(repository.save(product));
    }

    @Transactional
    public void delete(UUID productId) throws ResourceNotFoundException {
        Product product = findWithCatalog(productId);
        photoService.deleteStoredFiles(product.getPhotos());
        repository.delete(product);
    }

    private Product findWithCatalog(UUID productId) throws ResourceNotFoundException {
        return repository.findWithCatalogById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }
}
