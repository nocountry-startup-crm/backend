package com.nocountry.crm.dto.request;

import lombok.Data;

@Data
public class LoginDto {
    String email;
    String password;
    String companyCode;
}
