package com.ecommerce.ecommerce.common.audit;

import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("auditor")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();

        Object principal = auth.getPrincipal();
        if (principal instanceof User u && u.getId() != null) {
            return Optional.of(entityManager.getReference(User.class, u.getId()));
        }
        return Optional.empty();
    }
}
