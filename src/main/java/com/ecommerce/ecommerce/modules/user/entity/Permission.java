package com.ecommerce.ecommerce.modules.user.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import com.ecommerce.ecommerce.modules.user.entity.enums.PermissionName;
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
public class Permission extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private PermissionName permissionName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public @Nullable String getAuthority() {
        return permissionName.name();
    }
}
