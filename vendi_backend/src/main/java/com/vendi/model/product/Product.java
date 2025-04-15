package com.vendi.model.product;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.cart.CartItem;
import com.vendi.model.order.OrderItem;
import com.vendi.model.photo.Photo;
import com.vendi.model.rating.Rating;
import com.vendi.model.user.User;
import com.vendi.model.wishlist.WishlistItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Set;


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

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "main_photo_id")
    private Photo mainPhoto;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistItem> wishlistItems;

    @Min(1)
    private int quantity;

    @Min(1)
    private int installment;

    @Min(0)
    @Max(100)
    private int discount;
}
