package com.vendi.domain.product;

import com.vendi.domain.AbstractEditableEntity;
import com.vendi.domain.photo.Photo;
import com.vendi.domain.purchase.Purchase;
import com.vendi.domain.purchase.PurchaseItem;
import com.vendi.domain.rating.Rating;
import com.vendi.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.Date;


@Getter
@Setter
@Table(name = "product")
@Entity
public class Product extends AbstractEditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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