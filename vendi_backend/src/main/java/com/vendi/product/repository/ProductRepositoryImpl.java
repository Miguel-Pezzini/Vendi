package com.vendi.product.repository;

import com.vendi.product.dto.ProductQueryParams;
import com.vendi.product.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll(ProductQueryParams dto) {
        List<UUID> ids = findMatchingIds(dto);
        if (ids.isEmpty()) {
            return List.of();
        }

        List<Product> products = entityManager.createQuery(
                        "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.photos LEFT JOIN FETCH p.categories WHERE p.id IN :ids",
                        Product.class
                )
                .setParameter("ids", ids)
                .getResultList();

        Map<UUID, Product> productsById = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        return ids.stream()
                .map(productsById::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<UUID> findMatchingIds(ProductQueryParams dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UUID> query = cb.createQuery(UUID.class);
        Root<Product> product = query.from(Product.class);
        Join<Object, Object> categories = product.join("categories", jakarta.persistence.criteria.JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (dto.search() != null && !dto.search().isBlank()) {
            predicates.add(cb.like(cb.lower(product.get("name")), "%" + dto.search().toLowerCase() + "%"));
        }

        if (dto.categoryId() != null) {
            predicates.add(cb.equal(categories.get("id"), dto.categoryId()));
        }

        query.select(product.get("id"))
                .distinct(true)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.desc(product.get("createdAt")));

        TypedQuery<UUID> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(dto.resolvedPage() * dto.resolvedSize());
        typedQuery.setMaxResults(dto.resolvedSize());
        return typedQuery.getResultList();
    }
}
