package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.service.JwtService;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final UserService userService;
//    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody RequestUserDto dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        if(authentication.isAuthenticated())
            return ResponseEntity.ok("Success");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody RequestUserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(dto));
    }
}
