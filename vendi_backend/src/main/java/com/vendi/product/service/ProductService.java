package com.vendi.product.service;

import com.vendi.photo.dto.IsMainPhoto;
import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;
import com.vendi.photo.service.PhotoService;
import com.vendi.product.dto.*;
import com.vendi.product.exception.MaxPhotoLimitExceededException;
import com.vendi.product.mapper.ProductMapper;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.photo.model.Photo;
import com.vendi.category.model.Category;
import com.vendi.product.model.Product;
import com.vendi.shared.exception.ValidationExceptions.IllegalArgumentException;
import com.vendi.user.model.User;
import com.vendi.product.repository.ProductRepository;
import com.vendi.category.service.CategoryService;
import com.vendi.user.service.UserAuthenticatedService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository repository;
    private final UserAuthenticatedService userAuthenticatedService;
    private final PhotoService photoService;

    public ProductService(
            CategoryService categoryService,
            ProductRepository repository,
            UserAuthenticatedService userAuthenticatedService,
            PhotoService photoService) {
        this.categoryService = categoryService;
        this.repository = repository;
        this.userAuthenticatedService = userAuthenticatedService;
        this.photoService = photoService;
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) throws ResourceNotFoundException, IllegalArgumentException {
        Product product = ProductMapper.mapToProduct(productRequestDTO);

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(productRequestDTO.categoriesIds()));
        this.validateCategories(categories, productRequestDTO.categoriesIds());

        product.setUser(this.userAuthenticatedService.getAuthenticatedUser());
        product.setCategories(categories);

        List<Photo> photos = photoService.createPhotos(productRequestDTO.photosToCreate());
        this.validateMainPhoto(photos);
        for (Photo photo : photos) {
            product.addPhoto(photo);
        }

        return new ProductResponseDTO(this.repository.save(product));
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
    public List<ProductResponseDTO>getProducts(ProductQueryParams productQueryParams) {
        List<Product> products = this.repository.findAllByCustomFilter(productQueryParams);

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
    public ProductDetailsResponseDTO getDetailsById(UUID productId) throws ResourceNotFoundException {
        Optional<Product> product = repository.findById(productId);

        if(product.isEmpty()) {
            throw new ResourceNotFoundException("This product does not exists");
        }

        return new ProductDetailsResponseDTO(product.get());
    }



    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getLatestProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return this.repository.findRecentProducts(pageable).stream().map(ProductResponseDTO::new).toList();
    }

    public ProductResponseDTO update(UUID productId,  ProductRequestDTO productRequestDTO) throws ResourceNotFoundException, IllegalArgumentException {
        Product product = repository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Set<Category> categories = new HashSet<>(this.categoryService.findAllById(productRequestDTO.categoriesIds()));
        this.validateCategories(categories, productRequestDTO.categoriesIds());
        product.setCategories(categories);
        product.setName(productRequestDTO.name());
        product.setPrice(productRequestDTO.price());
        product.setQuantity(productRequestDTO.quantity());
        product.setInstallment(productRequestDTO.installment());
        product.setDiscount(productRequestDTO.discount());

        this.validatePhotos(productRequestDTO.photosToCreate(), productRequestDTO.photosToKeep());

        List<Photo> createdPhotos = photoService.createPhotos(productRequestDTO.photosToCreate());
        List<Photo> currentPhotos = product.getPhotos();

        Set<UUID> idsToKeep = productRequestDTO.photosToKeep().stream()
                .map(PhotoToKeepDTO::id)
                .collect(Collectors.toSet());

        currentPhotos.removeIf(photo -> !idsToKeep.contains(photo.getId()));

        for (Photo photo : createdPhotos) {
            product.addPhoto(photo);
        }

        return new ProductResponseDTO(repository.save(product));
    }

    private void validatePhotos(List<PhotoToCreateDTO> photosToCreate, List<PhotoToKeepDTO> photosToKeep) throws IllegalArgumentException {
        if(photosToCreate.size() + photosToKeep.size() > Product.MAX_PHOTO_LIMIT) {
            throw new MaxPhotoLimitExceededException(Product.MAX_PHOTO_LIMIT);
        }

        long mainPhotoCount = Stream.concat(
                        photosToCreate.stream(),
                        photosToKeep.stream()
                )
                .filter(IsMainPhoto::isMainPhoto)
                .count();
        if (mainPhotoCount > 1) {
            throw new IllegalArgumentException("Only one photo can be marked as the main photo.");
        }
    }

    public void delete(UUID productId) {
        repository.deleteById(productId);
    }
}
