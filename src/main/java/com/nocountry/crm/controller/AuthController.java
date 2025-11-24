package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.RegisterDto;
import com.nocountry.crm.dto.response.AuthResponseDto;
import com.nocountry.crm.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nocountry.crm.common.ApiPaths.AUTH_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH_BASE)
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('CUSTOMER_ADMIN')")
    public ResponseEntity<AuthResponseDto> register(Authentication authentication,
                                                    @RequestBody RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authentication.getName(), registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
