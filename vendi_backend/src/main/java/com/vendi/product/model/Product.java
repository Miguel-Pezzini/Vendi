package com.vendi.product.model;

import com.vendi.shared.model.AbstractEditableEntity;
import com.vendi.cart.model.CartItem;
import com.vendi.order.model.OrderItem;
import com.vendi.photo.model.Photo;
import com.vendi.category.model.Category;
import com.vendi.rating.model.Rating;
import com.vendi.user.model.User;
import com.vendi.wishlist.model.WishlistItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Table(name = "product")
@Entity
public class Product extends AbstractEditableEntity {

    @Transient
    public static final int MAX_PHOTO_LIMIT = 5;

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
    private List<Photo> photos = new ArrayList<>();

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

    @Min(0)
    private int installment;

    @Min(0)
    @Max(100)
    private int discount;

    public void addPhoto(Photo photo) {
        photo.setProduct(this);
        this.photos.add(photo);
    }
}
