package com.vendi.domain.rating;

import com.vendi.domain.product.Product;
import com.vendi.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Table(name = "rating")
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    private Date createdAt;

    public Rating() {
        this.createdAt = new Date();
    }

    // Getters e Setters
}

