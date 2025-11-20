package com.nocountry.crm.dto.response;

import com.nocountry.crm.entity.enums.RoleCode;

public record ResponseUserDto(
        String imageUrl,
        String fullName,
        String email,
        String companyCode,
        RoleCode role
) {
}
