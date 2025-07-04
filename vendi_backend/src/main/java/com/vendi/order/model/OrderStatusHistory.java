package com.vendi.order.model;

import com.vendi.shared.model.AbstractEditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory extends AbstractEditableEntity {
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "changed_at")
    private Date changedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
