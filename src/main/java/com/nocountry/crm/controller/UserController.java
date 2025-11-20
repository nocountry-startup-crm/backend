package com.nocountry.crm.controller;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.nocountry.crm.common.ApiPaths.USER_BASE;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER_BASE)
public class UserController {
    private final UserService userService;
    // get user by email
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable UUID id) {
        ResponseUserDto user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    // get all users
    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable UUID id, @RequestBody RequestUserDto dto) {
        ResponseUserDto updatedUser = userService.updateUser(id, dto);
        if(updatedUser == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedUser);
    }

    // delete by email
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
