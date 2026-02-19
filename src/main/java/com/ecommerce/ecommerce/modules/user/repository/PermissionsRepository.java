package com.ecommerce.ecommerce.modules.user.repository;

import com.ecommerce.ecommerce.modules.user.entity.Permission;
import com.ecommerce.ecommerce.modules.user.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permission, UUID> {
    List<Permission> findAllByRole_Role(RoleName roleRole);
}