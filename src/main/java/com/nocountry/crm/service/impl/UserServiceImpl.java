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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;

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

    @Override
    public ResponseUserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return mapper.toResponse(user);
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ResponseUserDto updateUser(UUID id, RequestUserDto dto, MultipartFile image) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String imageLink;

        try {
            imageLink = cloudinaryService.uploadImage(image).getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        user.setImageUrl(imageLink);
        if (dto.fullName() != null) user.setFullName(dto.fullName());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.password() != null) {
            user.setPassword(dto.password());
        }
//        if (dto.companyCode() != null) user.setCompanyId(dto.companyCode());
        if (dto.role() != null) user.setRole(dto.role());

        User saved = userRepository.save(user);
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) return;

        userRepository.deleteById(id);
    }
}
