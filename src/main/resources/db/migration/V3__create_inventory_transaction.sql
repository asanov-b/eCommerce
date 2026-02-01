CREATE TABLE inventory_transaction
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   UUID,
    updated_by   UUID,
    product_id   UUID                        NOT NULL,
    type         VARCHAR(255)                NOT NULL,
    reason       VARCHAR(255)                NOT NULL,
    quantity     INTEGER                     NOT NULL,
    reference_id UUID,
    CONSTRAINT pk_inventorytransaction PRIMARY KEY (id)
);

ALTER TABLE inventory_transaction
    ADD CONSTRAINT FK_INVENTORYTRANSACTION_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE inventory_transaction
    ADD CONSTRAINT FK_INVENTORYTRANSACTION_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

CREATE INDEX idx_product_items_product_id ON inventory_transaction (product_id);

ALTER TABLE inventory_transaction
    ADD CONSTRAINT FK_INVENTORYTRANSACTION_ON_UPDATED_BY FOREIGN KEY (updated_by) REFERENCES users (id);

ALTER TABLE public.product
    DROP COLUMN version;

ALTER TABLE product_income
    DROP CONSTRAINT fk69s8cmbq8ar4kf4e6p00ar358;

ALTER TABLE product_outcome
    DROP CONSTRAINT fk7rsqy1kb1obwb4pq85xel0l9n;

ALTER TABLE product_outcome
    DROP CONSTRAINT fk8c9ga8cwfrh4ml4s8hvmij0s8;

ALTER TABLE product_income
    DROP CONSTRAINT fkm9g1v339a0ye4di5241r14a1c;

ALTER TABLE product_income
    DROP CONSTRAINT fkmseanvuw1qf2c6jkf2u8pkk31;

ALTER TABLE product_outcome
    DROP CONSTRAINT fkskj4uc6o0oke15bx7hfg3aw1s;

DROP TABLE product_income CASCADE;

DROP TABLE product_outcome CASCADE;