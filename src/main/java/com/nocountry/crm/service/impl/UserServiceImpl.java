package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.entity.Role;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.RoleCode;
import com.nocountry.crm.exception.UserNotFoundException;
import com.nocountry.crm.mapper.UserMapper;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public ResponseUserDto saveUser(RequestUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(RoleCode.USER);
        userRepository.save(user);

        return mapper.toResponse(
                userRepository.findById(user.getId())
                        .orElseThrow(()-> new UserNotFoundException(user.getId())));
    }
}
