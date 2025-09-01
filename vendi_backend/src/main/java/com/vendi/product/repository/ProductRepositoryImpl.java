package com.vendi.product.repository;

import com.vendi.product.dto.ProductQueryParams;
import com.vendi.product.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAllByCustomFilter(ProductQueryParams dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        query.select(product)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.desc(product.get("createdAt")));

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        if (dto.limit() > 0) {
            typedQuery.setMaxResults(dto.limit());
        }

        return typedQuery.getResultList();
    }
}
