package com.vendi.category.repository;

import com.vendi.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, UUID> {

}
