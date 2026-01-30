package com.ecommerce.ecommerce.modules.auth.entity;

import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiresAt;
    @Column(nullable = false)
    private Boolean revoked;
    @Column(nullable = false)
    private Instant createdAt;
}
