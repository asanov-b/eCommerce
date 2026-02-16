package com.ecommerce.ecommerce.modules.user.service;

import com.ecommerce.ecommerce.modules.user.dto.request.ChangePasswordDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateRolesDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateUserDTO;
import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface UserService {

    @PreAuthorize("hasRole('ADMIN')")
    UserDTO updateProfile(UUID id, UpdateUserDTO updateUserDTO);

    @PreAuthorize("hasRole('USER')")
    void changePassword(UUID id, ChangePasswordDTO changePasswordDTO);

    @PreAuthorize("hasRole('ADMIN')")
    Page<UserDTO> getAllUser(Integer page, Integer size);

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    UserDTO getUser(UUID userId);

    @PreAuthorize("hasRole('ADMIN')")
    UserDTO changeRoles(UUID userId, UpdateRolesDTO updateRolesDTO);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(UUID userId, UUID currentUserId);
}
