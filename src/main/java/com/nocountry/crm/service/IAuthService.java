package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.SignupDto;
import com.nocountry.crm.dto.response.AuthResponseDto;

public interface IAuthService {
    AuthResponseDto signup(SignupDto signupDto);
    AuthResponseDto login(LoginDto loginDto);
}
