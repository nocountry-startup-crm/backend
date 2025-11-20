package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.LoginDto;
import com.nocountry.crm.dto.request.SignupDto;
import com.nocountry.crm.dto.response.AuthResponseDto;
import com.nocountry.crm.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    //private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        /*Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        if(authentication.isAuthenticated())
            return ResponseEntity.ok("Success");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        */
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody SignupDto signupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupDto));
    }
}
