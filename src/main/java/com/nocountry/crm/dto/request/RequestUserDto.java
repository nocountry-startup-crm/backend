package com.nocountry.crm.dto.request;

public record RequestUserDto(
        String email,
        String password,
        String companyCode
        )
{}
