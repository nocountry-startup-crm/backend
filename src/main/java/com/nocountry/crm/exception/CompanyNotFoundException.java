package com.nocountry.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class CompanyNotFoundException extends ResponseStatusException {

    public CompanyNotFoundException(String companyCode) {
        super(HttpStatus.NOT_FOUND, "Company not found with Company Code: " + companyCode);
    }
}

