package com.ecommerce.ecommerce.modules.user.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.user.dto.request.ChangePasswordDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateRolesDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateUserDTO;
import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.User;
import com.ecommerce.ecommerce.modules.user.entity.enums.RoleName;
import com.ecommerce.ecommerce.modules.user.mapper.UserMapper;
import com.ecommerce.ecommerce.modules.user.repository.RoleRepository;
import com.ecommerce.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDTO updateProfile(UUID id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        if (!dto.email().equals(user.getEmail()) && userRepository.existsByEmail(dto.email())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        log.info("update user userId {}", id);
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public void changePassword(UUID id, ChangePasswordDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.confirmPassword()));
        log.info("Password changed userId= {}", user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUser(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> all = userRepository.findAll(pageable);
        return all.map(userMapper::toUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUser(UUID userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
        }

        User currentUser = (User) auth.getPrincipal();
        boolean isAdmin = currentUser.getRoles().stream()
                .map(Role::getRole)
                .anyMatch(RoleName.ADMIN::equals);
        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO changeRoles(UUID userId, UpdateRolesDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        List<Role> roles = roleRepository.findAllByRoleIn(dto.roles());
        user.setRoles(roles);
        log.info("Change roles userId= {}", user.getId());
        return userMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(UUID userId, UUID currentUserId) {
        if (userId.equals(currentUserId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You are not allowed to delete");
        }
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            log.info("Delete user userId= {} ", userId);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
