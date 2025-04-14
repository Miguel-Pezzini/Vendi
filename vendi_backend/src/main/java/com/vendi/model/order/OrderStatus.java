package com.vendi.model.order;

public enum OrderStatus {
    ADMIN("admin"),
    USER("user");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
