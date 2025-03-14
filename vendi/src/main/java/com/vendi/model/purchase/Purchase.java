package com.vendi.model.purchase;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Table(name = "purchase")
@Entity
public class Purchase extends AbstractEditableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchases = new ArrayList<>();
}
