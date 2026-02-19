package com.ecommerce.ecommerce.modules.user.repository;

import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {


    List<Role> findAllByRoleIn(Collection<RoleName> roles);

    Optional<Role> findByRole(RoleName role);
}