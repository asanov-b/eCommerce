package com.ecommerce.ecommerce.modules.auth.service;

import com.ecommerce.ecommerce.modules.auth.dto.LoginDTO;
import com.ecommerce.ecommerce.modules.auth.dto.RegisterDTO;
import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;

public interface AuthService {

    TokenDTO login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
