package com.ecommerce.ecommerce.modules.user.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role;

    @Override
    public @Nullable String getAuthority() {
        return "ROLE_" + role.name();
    }
}
