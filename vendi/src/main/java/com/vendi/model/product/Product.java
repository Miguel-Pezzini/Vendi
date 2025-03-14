package com.vendi.model.product;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.photo.Photo;
import com.vendi.model.purchase.PurchaseItem;
import com.vendi.model.rating.Rating;
import com.vendi.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Table(name = "product")
@Entity
public class Product extends AbstractEditableEntity {
    private String name;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchasesItems;

    @Min(1)
    private int quantity;

    @Min(1)
    private int installment;

    @Min(0)
    @Max(100)
    private int discount;
}