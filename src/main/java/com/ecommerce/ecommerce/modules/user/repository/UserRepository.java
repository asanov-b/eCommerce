package com.ecommerce.ecommerce.modules.user.repository;

import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndRolesContains(UUID id, List<Role> roles);
}