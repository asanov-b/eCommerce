package com.ecommerce.ecommerce.modules.user.dto.request;

import com.ecommerce.ecommerce.modules.user.entity.RoleName;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateRolesDTO(
        @NotEmpty
        List<RoleName> roles
) {}
