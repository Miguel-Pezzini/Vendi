package com.vendi.model.photo;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends AbstractEditableEntity {
    @Lob // Indica que é um dado grande (usado para armazenar imagens como BLOB no banco)
    @Column(columnDefinition = "BYTEA") // Para PostgreSQL, use "LONGBLOB" no MySQL
    private byte[] data;

    private String contentType; // Ex: "image/png", "image/jpeg"

    private String filename; // Nome original do arquivo

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Chave estrangeira
    private Product product;
}
