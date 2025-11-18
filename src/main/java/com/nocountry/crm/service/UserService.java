package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;

public interface UserService {
    ResponseUserDto saveUser(RequestUserDto user);
}
