package com.vendi.photo.model;

import com.vendi.shared.model.AbstractEditableEntity;
import com.vendi.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends AbstractEditableEntity {
    @Column(name = "storage_key", nullable = false, length = 512)
    private String storageKey;

    private String contentType;

    private String filename;

    @Column(nullable = false)
    private Boolean isMain = false;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
