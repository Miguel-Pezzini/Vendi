package com.vendi.model.photo;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends AbstractEditableEntity {
    @Lob
    private byte[] data;

    private String contentType;

    private String filename;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
