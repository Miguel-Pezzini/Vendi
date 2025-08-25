package com.vendi.address.model;

import com.vendi.shared.model.AbstractEditableEntity;
import com.vendi.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends AbstractEditableEntity {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
