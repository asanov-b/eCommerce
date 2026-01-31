CREATE TABLE orders
(
    id           UUID           NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   UUID,
    updated_by   UUID,
    order_status VARCHAR(255)   NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    user_id      UUID           NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id                    UUID           NOT NULL,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by            UUID,
    updated_by            UUID,
    order_id              UUID           NOT NULL,
    product_id            UUID           NOT NULL,
    product_name_snapshot VARCHAR(255)   NOT NULL,
    unit_price_snapshot   DECIMAL(19, 2) NOT NULL,
    quantity              INTEGER        NOT NULL ,
    total_amount          DECIMAL(19, 2) NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

CREATE INDEX idx_order_items_order_id ON order_item (order_id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_UPDATED_BY FOREIGN KEY (updated_by) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDER_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDER_ON_UPDATED_BY FOREIGN KEY (updated_by) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

