package com.vendi.photo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] data;

    private String contentType;

    private String filename;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
