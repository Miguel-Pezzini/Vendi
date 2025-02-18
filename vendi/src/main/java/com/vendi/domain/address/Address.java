package com.vendi.domain.address;

import com.vendi.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
