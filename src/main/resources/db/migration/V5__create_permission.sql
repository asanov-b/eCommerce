CREATE TABLE permission
(
    id              UUID NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by      UUID,
    updated_by      UUID,
    permission_name VARCHAR(255),
    role_id         UUID,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

ALTER TABLE permission
    ADD CONSTRAINT FK_PERMISSIONS_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE permission
    ADD CONSTRAINT FK_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE permission
    ADD CONSTRAINT FK_PERMISSIONS_ON_UPDATED_BY FOREIGN KEY (updated_by) REFERENCES users (id);

DO $$
DECLARE
    admin_role_id UUID;
    user_role_id UUID;
BEGIN
    SELECT id INTO admin_role_id FROM roles WHERE role = 'ADMIN';
    SELECT id INTO user_role_id FROM roles WHERE role = 'USER';

    INSERT INTO permission (id, permission_name, role_id, created_at, updated_at) VALUES
        (gen_random_uuid(), 'AUTH_LOGOUT', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_READ_OWN', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_UPDATE_OWN', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_PASSWORD_UPDATE_OWN', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_READ', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_UPDATE_ROLES', admin_role_id, now(), now()),
        (gen_random_uuid(), 'USER_DELETE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'PRODUCT_READ', admin_role_id, now(), now()),
        (gen_random_uuid(), 'PRODUCT_CREATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'PRODUCT_UPDATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'PRODUCT_DELETE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'CATEGORY_READ', admin_role_id, now(), now()),
        (gen_random_uuid(), 'CATEGORY_CREATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'CATEGORY_UPDATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'CATEGORY_DELETE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'FILE_CREATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'ORDER_READ', admin_role_id, now(), now()),
        (gen_random_uuid(), 'ORDER_UPDATE_STATUS', admin_role_id, now(), now()),
        (gen_random_uuid(), 'INVENTORY_CREATE', admin_role_id, now(), now()),
        (gen_random_uuid(), 'INVENTORY_READ', admin_role_id, now(), now());

    INSERT INTO permission (id, permission_name, role_id, created_at, updated_at) VALUES
        (gen_random_uuid(), 'AUTH_LOGOUT', user_role_id, now(), now()),
        (gen_random_uuid(), 'USER_READ_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'USER_UPDATE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'USER_PASSWORD_UPDATE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'PRODUCT_READ', user_role_id, now(), now()),
        (gen_random_uuid(), 'CATEGORY_READ', user_role_id, now(), now()),
        (gen_random_uuid(), 'CART_READ_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'CART_CREATE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'CART_UPDATE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'CART_DELETE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'ORDER_CREATE_OWN', user_role_id, now(), now()),
        (gen_random_uuid(), 'ORDER_READ_OWN', user_role_id, now(), now());
END $$;