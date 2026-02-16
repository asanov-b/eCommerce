package com.ecommerce.ecommerce.modules.user.mapper;

import com.ecommerce.ecommerce.modules.user.dto.response.UserDTO;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UserMapper {

    private final ZoneId zoneId = ZoneId.of("Asia/Tashkent");

    public UserDTO toUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                LocalDateTime.ofInstant(user.getCreatedAt(), zoneId),
                LocalDateTime.ofInstant(user.getUpdatedAt(), zoneId),
                user.getRoles().stream().map(Role::getAuthority).toList()
        );
    }
}
