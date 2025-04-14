package com.vendi.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEditableEntity extends AbstractEntity {
    @Column(name = "updated_at")
    private Date updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    public AbstractEditableEntity() {
        super(); // Chama o construtor que define createdAt automaticamente
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
        // O Spring automaticamente preencherá o updatedBy se configurado corretamente
    }
}
