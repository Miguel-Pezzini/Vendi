package com.vendi.rating.model;

import com.vendi.shared.model.AbstractEditableEntity;
import com.vendi.product.model.Product;
import com.vendi.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "rating")
@Entity
public class Rating extends AbstractEditableEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Min(1)
    @Max(5)
    private int stars; // Rating de 1 a 5 estrelas

    private String comment;
}

