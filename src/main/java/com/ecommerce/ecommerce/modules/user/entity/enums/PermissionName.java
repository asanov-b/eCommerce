package com.ecommerce.ecommerce.modules.user.entity.enums;

public enum PermissionName {
    AUTH_LOGOUT, //(authenticated)
    USER_READ_OWN, //(GET /users/me)
    USER_UPDATE_OWN, //(PUT /users/me)
    USER_PASSWORD_UPDATE_OWN, //(PUT /users/me/password)
    USER_READ, //(GET /admin/users, GET /admin/users/{id})
    USER_UPDATE_ROLES, //(PUT /admin/users/{id}/roles)
    USER_DELETE, //(DELETE /admin/users/{id})
    PRODUCT_READ, //(GET /products, GET /products/{id}, POST /products/search)
    PRODUCT_CREATE, //(POST /products)
    PRODUCT_UPDATE, //(PUT /products/{id})
    PRODUCT_DELETE, //(DELETE /products/{id})
    CATEGORY_READ, //(GET /category, GET /category/{id})
    CATEGORY_CREATE, //(POST /category)
    CATEGORY_UPDATE, //(PUT /category/{id})
    CATEGORY_DELETE, //(DELETE /category/{id})
    FILE_CREATE, //(POST /files)
    CART_READ_OWN, //(GET /cart)
    CART_CREATE_OWN, //(POST /cart)
    CART_UPDATE_OWN, //(PUT /cart/items/{itemId})
    CART_DELETE_OWN, //(DELETE /cart/items/{itemId}, DELETE /cart/clear)
    ORDER_CREATE_OWN, //(POST /orders)
    ORDER_READ_OWN, //(GET /orders/my, GET /orders/{id} user)
    ORDER_READ, //(GET /orders) // admin
    ORDER_UPDATE_STATUS, //(PATCH /orders/{id}/status) // admin
    INVENTORY_CREATE, //(POST /admin/inventory/in)
    INVENTORY_READ //(GET /admin/inventory/stock/{productId}, GET /admin/inventory/history)
}
