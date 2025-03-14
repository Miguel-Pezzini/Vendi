package com.vendi.model.purchase;

import com.vendi.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "purchase_item")
@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Min(1)
    private int quantity;

    private Float unitPrice; // Para armazenar o preço no momento da compra

    public PurchaseItem() {}

    public PurchaseItem(Purchase purchase, Product product, int quantity, Float unitPrice) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
