package com.vendi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.Date;

@Getter
@MappedSuperclass
public class AbstractEntity {
    @Column(name = "created_at")
    private final Date createdAt;

    protected AbstractEntity() {
        this.createdAt = new Date(); // Define a data automaticamente
    }
}
