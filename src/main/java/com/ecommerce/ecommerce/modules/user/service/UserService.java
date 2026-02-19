package com.ecommerce.ecommerce.modules.user.service;

import com.ecommerce.ecommerce.modules.user.dto.request.ChangePasswordDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateRolesDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateUserDTO;
import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserDTO updateProfile(UUID id, UpdateUserDTO updateUserDTO);

    void changePassword(UUID id, ChangePasswordDTO changePasswordDTO);

    Page<UserDTO> getAllUser(Integer page, Integer size);

    UserDTO getUser(UUID userId);

    UserDTO changeRoles(UUID userId, UpdateRolesDTO updateRolesDTO);

    void deleteUser(UUID userId, UUID currentUserId);
}
