package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    ResponseUserDto saveUser(RequestUserDto user);

    ResponseUserDto getUserById(UUID id);

    List<ResponseUserDto> getAllUsers();

    ResponseUserDto updateUser(UUID id, RequestUserDto dto);

    void deleteUser(UUID id);
}
