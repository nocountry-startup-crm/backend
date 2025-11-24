package com.nocountry.crm.dto.request;

import lombok.Data;

@Data
public class RegisterDto {
    String fullName;
    String email;
    String password;
    String companyCode;
}
