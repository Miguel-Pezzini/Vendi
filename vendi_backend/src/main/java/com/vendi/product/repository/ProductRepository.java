package com.vendi.product.repository;

import com.vendi.product.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findRecentProducts(Pageable pageable);

    @EntityGraph(attributePaths = {"photos", "categories"})
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findWithCatalogById(@Param("id") UUID id);

    List<Product> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId AND p.quantity >= :quantity")
    int decrementStock(@Param("productId") UUID productId, @Param("quantity") int quantity);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.id = :productId")
    int incrementStock(@Param("productId") UUID productId, @Param("quantity") int quantity);
}
