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

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll(ProductQueryParams dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);
        Join<Object, Object> categories = product.join("categories", jakarta.persistence.criteria.JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (dto.search() != null && !dto.search().isBlank()) {
            predicates.add(cb.like(cb.lower(product.get("name")), "%" + dto.search().toLowerCase() + "%"));
        }

        if (dto.categoryId() != null) {
            predicates.add(cb.equal(categories.get("id"), dto.categoryId()));
        }

        query.select(product)
                .distinct(true)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.desc(product.get("createdAt")));

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        if (dto.limit() != null && dto.limit() > 0) {
            typedQuery.setMaxResults(dto.limit());
        }

        return typedQuery.getResultList();
    }
}
