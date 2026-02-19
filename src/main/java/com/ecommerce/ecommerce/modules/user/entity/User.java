package com.ecommerce.ecommerce.modules.user.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(String email, List<Role> authorities) {
        this.email = email;
        this.roles = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(role);
            authorities.addAll(role.getPermissions());
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
