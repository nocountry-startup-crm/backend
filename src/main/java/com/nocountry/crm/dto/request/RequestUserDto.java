package com.nocountry.crm.dto.request;

import com.nocountry.crm.entity.enums.RoleCode;

public record RequestUserDto(
        String fullName,
        String email,
        String password,
        String companyCode
        )
{}
