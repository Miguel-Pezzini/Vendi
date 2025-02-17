package com.vendi.domain.photo;

import com.vendi.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob // Indica que é um dado grande (usado para armazenar imagens como BLOB no banco)
    @Column(columnDefinition = "BYTEA") // Para PostgreSQL, use "LONGBLOB" no MySQL
    private byte[] data;

    private String contentType; // Ex: "image/png", "image/jpeg"

    private String filename; // Nome original do arquivo

    private Date uploadedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Chave estrangeira
    private Product product;
}
