package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.SignupDto;
import com.nocountry.crm.dto.response.AuthResponseDto;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.RoleCode;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.security.JwtTokenProvider;
import com.nocountry.crm.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto signup(SignupDto signupDto) {
        User user = User.builder()
                .fullName(signupDto.getFullName())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(RoleCode.USER)
                .build();
        user.setCompanyId(999); // get id by companyCode
        userRepository.save(user);
        //user.setCreatedUserId();
        //user.getUpdatedUserId();
        String token = jwtTokenProvider.generateToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        // validar loginDto.getCompanyCode()

        //User user = (User) authentication.getPrincipal();
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String token = jwtTokenProvider.generateToken(user);
        return AuthResponseDto.builder()
                //.token(user.getAuthorities().toString())
                .token(token)
                .build();
    }
}