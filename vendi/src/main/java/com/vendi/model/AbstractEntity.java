package com.vendi.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at")
    private final Date createdAt;

    protected AbstractEntity() {
        this.createdAt = new Date(); // Define a data automaticamente
    }
}
