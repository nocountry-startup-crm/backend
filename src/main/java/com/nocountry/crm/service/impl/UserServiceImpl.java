package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.entity.Company;
import com.nocountry.crm.entity.Role;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.RoleCode;
import com.nocountry.crm.exception.CompanyNotFoundException;
import com.nocountry.crm.exception.FunctionalException;
import com.nocountry.crm.exception.UserNotFoundException;
import com.nocountry.crm.mapper.UserMapper;
import com.nocountry.crm.repository.ICompanyRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final ICompanyRepository companyRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;

    @Override
    public ResponseUserDto saveUser(RequestUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new FunctionalException("Email already in use.", HttpStatus.CONFLICT);
        }

        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(RoleCode.USER);
        userRepository.save(user);

        return mapper.toResponse(
                userRepository.findById(user.getId())
                        .orElseThrow(()-> new FunctionalException(
                                "User not found with id " + user.getId() +
                                        ". Please ensure the user exists in the system.",
                                HttpStatus.NOT_FOUND)));
    }

    @Override
    public ResponseUserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new FunctionalException(
                        "User not found with id " + id + ". Please ensure the user exists in the system.",
                        HttpStatus.NOT_FOUND));

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
                .orElseThrow(() -> new FunctionalException(
                        "User not found with id " + id + ". Please ensure the user exists in the system.",
                        HttpStatus.NOT_FOUND));

        if (image != null) {
            String imageLink;
            try {
                imageLink = cloudinaryService.uploadImage(image).getUrl();
            } catch (IOException e) {
                throw new RuntimeException("Cloudinary IO Exception. " + e);
            }
            user.setImageUrl(imageLink);
        }

        if (dto.fullName() != null) user.setFullName(dto.fullName());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.password() != null) {
            user.setPassword(dto.password());
        }
        if (dto.companyCode() != null) {
            Company company = companyRepository.findByCode(dto.companyCode())
                    .orElseThrow(() -> new FunctionalException(
                            "Company not found with code " + dto.companyCode() +
                                    ". Please ensure the company code exists in the system.",
                            HttpStatus.NOT_FOUND));
            user.setCompany(company);
        }

        User saved = userRepository.save(user);
        return mapper.toResponse(saved);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new FunctionalException(
                        "User not found with id " + id + ". Please ensure the user exists in the system.",
                        HttpStatus.NOT_FOUND));

        user.setDeleted(true);
        userRepository.save(user);

        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new FunctionalException(
                        "User not found with email " + userEmail + ". Please ensure the user exists in the system.",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new FunctionalException(
                        "User not found with id " + id + ". Please ensure the user exists in the system.",
                        HttpStatus.NOT_FOUND));
    }
}
