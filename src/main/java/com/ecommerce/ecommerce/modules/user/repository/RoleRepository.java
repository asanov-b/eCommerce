package com.ecommerce.ecommerce.modules.user.repository;

import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {


    List<Role> findAllByRole(RoleName role);

    Optional<Role> findByRole(RoleName role);
}