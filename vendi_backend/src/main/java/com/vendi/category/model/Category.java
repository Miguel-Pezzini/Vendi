package com.vendi.category.model;

import com.vendi.shared.model.AbstractEditableEntity;
import com.vendi.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends AbstractEditableEntity {
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_father_id")
    private Category categoryFather;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "categoryFather")
    private Set<Category> subcategories = new HashSet<>();
}
