package com.nocountry.crm.dto.request;

import com.nocountry.crm.entity.enums.RoleCode;

public record RequestUserDto(
        String imageUrl,
        String fullName,
        String email,
        String password,
        String companyCode,
        RoleCode role
        )
{}
