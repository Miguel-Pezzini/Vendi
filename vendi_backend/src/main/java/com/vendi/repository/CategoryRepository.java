package com.vendi.repository;

import com.vendi.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, UUID> {

}
