package com.ecommerce.ecommerce.modules.user.controller;

import com.ecommerce.ecommerce.modules.user.dto.request.ChangePasswordDTO;
import com.ecommerce.ecommerce.modules.user.dto.request.UpdateUserDTO;
import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import com.ecommerce.ecommerce.modules.user.entity.User;
import com.ecommerce.ecommerce.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.ecommerce.common.utils.AppPaths.API;
import static com.ecommerce.ecommerce.common.utils.AppPaths.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/users/me")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User principal) {
        UserDTO userDTO = userService.getUser(principal.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateProfile(@AuthenticationPrincipal User principal, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UserDTO userDTO = userService.updateProfile(principal.getId(), updateUserDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal User principal, @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(principal.getId(), changePasswordDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
