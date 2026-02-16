package com.ecommerce.ecommerce.modules.user.controller;

import com.ecommerce.ecommerce.modules.user.dto.request.UpdateRolesDTO;
import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import com.ecommerce.ecommerce.modules.user.entity.User;
import com.ecommerce.ecommerce.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/admin/users")
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Page<UserDTO> userDTOs = userService.getAllUser(page, size);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        UserDTO userDTO = userService.getUser(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<UserDTO> updateUserRoles(@PathVariable UUID userId, @Valid @RequestBody UpdateRolesDTO updateRolesDTO) {
        UserDTO userDTO = userService.changeRoles(userId, updateRolesDTO);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId, @AuthenticationPrincipal User principal) {
        userService.deleteUser(userId, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
