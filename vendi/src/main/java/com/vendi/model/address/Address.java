package com.vendi.model.address;

import com.vendi.model.AbstractEditableEntity;
import com.vendi.model.user.User;
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
    private String cep;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
