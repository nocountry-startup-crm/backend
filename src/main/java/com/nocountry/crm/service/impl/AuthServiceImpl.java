package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RegisterDto;
import com.nocountry.crm.dto.response.AuthResponseDto;
import com.nocountry.crm.entity.Company;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.RoleCode;
import com.nocountry.crm.repository.ICompanyRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.security.JwtTokenProvider;
import com.nocountry.crm.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ICompanyRepository companyRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(String creatorUserEmail, RegisterDto registerDto) {
        Company company = companyRepository.findByCode(registerDto.getCompanyCode())
                .orElseThrow(() -> new RuntimeException("Company not found."));
        User creatorUser = userRepository.findByEmail(creatorUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found."));

        User user = User.builder()
                .fullName(registerDto.getFullName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(RoleCode.USER)
                .company(company)
                .createdUserId(creatorUser.getId())
                .updatedUserId(creatorUser.getId())
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .userId(savedUser.getId())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Bad credentials."));

        if (!Objects.equals(user.getCompany().getCode(), loginDto.getCompanyCode())) {
            throw new RuntimeException("Bad credentials.");
        }

        String token = jwtTokenProvider.generateToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .build();
    }
}