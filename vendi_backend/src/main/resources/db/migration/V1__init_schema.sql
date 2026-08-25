CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    current_address_id UUID,
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE category (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    name VARCHAR(255),
    description VARCHAR(255),
    category_father_id UUID REFERENCES category (id)
);

CREATE TABLE product (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    version INTEGER NOT NULL DEFAULT 0,
    name VARCHAR(255),
    price NUMERIC(12, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    installment INTEGER NOT NULL,
    discount INTEGER NOT NULL,
    user_id UUID NOT NULL REFERENCES users (id)
);

CREATE INDEX idx_product_created_at ON product (created_at DESC);
CREATE INDEX idx_product_user_id ON product (user_id);

CREATE TABLE product_category (
    product_id UUID NOT NULL REFERENCES product (id) ON DELETE CASCADE,
    category_id UUID NOT NULL REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, category_id)
);

CREATE TABLE photo (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    storage_key VARCHAR(512) NOT NULL,
    content_type VARCHAR(255),
    filename VARCHAR(255),
    is_main BOOLEAN NOT NULL DEFAULT FALSE,
    product_id UUID NOT NULL REFERENCES product (id) ON DELETE CASCADE
);

CREATE INDEX idx_photo_product_id ON photo (product_id);

CREATE TABLE address (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(255),
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

ALTER TABLE users
    ADD CONSTRAINT fk_users_current_address
        FOREIGN KEY (current_address_id) REFERENCES address (id);

CREATE TABLE cart (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    version INTEGER NOT NULL DEFAULT 0,
    user_id UUID NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    quantity INTEGER NOT NULL,
    cart_id UUID NOT NULL REFERENCES cart (id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES product (id),
    CONSTRAINT uk_cart_item_cart_product UNIQUE (cart_id, product_id)
);

CREATE TABLE wishlist (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    user_id UUID NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE wishlist_item (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    quantity INTEGER NOT NULL,
    wishlist_id UUID NOT NULL REFERENCES wishlist (id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES product (id),
    CONSTRAINT uk_wishlist_item_wishlist_product UNIQUE (wishlist_id, product_id)
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    status VARCHAR(255),
    payment_provider VARCHAR(255) NOT NULL,
    stripe_checkout_session_id VARCHAR(255) UNIQUE,
    stripe_payment_intent_id VARCHAR(255),
    customer_name VARCHAR(255) NOT NULL,
    company_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    city VARCHAR(255) NOT NULL,
    total_amount NUMERIC(12, 2) NOT NULL,
    user_id UUID NOT NULL REFERENCES users (id)
);

CREATE INDEX idx_orders_user_created_at ON orders (user_id, created_at DESC);

CREATE TABLE order_item (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12, 2) NOT NULL,
    order_id UUID NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES product (id)
);

CREATE TABLE order_status_history (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    changed_at TIMESTAMP(6),
    status VARCHAR(255),
    order_id UUID NOT NULL REFERENCES orders (id) ON DELETE CASCADE
);

CREATE TABLE rating (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    stars INTEGER NOT NULL,
    comment VARCHAR(255),
    product_id UUID NOT NULL REFERENCES product (id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE stripe_webhook_event (
    event_id VARCHAR(255) PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    processed_at TIMESTAMP(6) NOT NULL
);
