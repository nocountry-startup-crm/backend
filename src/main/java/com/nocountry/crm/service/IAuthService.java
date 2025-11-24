package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RegisterDto;
import com.nocountry.crm.dto.response.AuthResponseDto;

public interface IAuthService {
    AuthResponseDto register(String creatorUserEmail, RegisterDto registerDto);
    AuthResponseDto login(LoginDto loginDto);
}
