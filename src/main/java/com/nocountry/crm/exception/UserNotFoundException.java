package com.nocountry.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(UUID userId) {
        super(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
    }
}
