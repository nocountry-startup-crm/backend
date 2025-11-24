package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.nocountry.crm.common.ApiPaths.USER_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER_BASE)
public class UserController {
    private final UserService userService;
    // get user by email
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable UUID id) {
        ResponseUserDto user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    // get all users
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // update
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(
            @PathVariable UUID id,
            @RequestPart("user") RequestUserDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        ResponseUserDto updatedUser = userService.updateUser(id, dto, image);
        if(updatedUser == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedUser);
    }

    // update
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @PutMapping()
    public ResponseEntity<ResponseUserDto> updateUser(
            @RequestPart("user") RequestUserDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        UUID id = UUID.randomUUID();
        ResponseUserDto updatedUser = userService.updateUser(id, dto, image);
        if(updatedUser == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedUser);
    }

    // delete by email
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
