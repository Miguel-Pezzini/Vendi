package com.vendi.domain.purchase;

import com.vendi.domain.AbstractEditableEntity;
import com.vendi.domain.user.User;
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
