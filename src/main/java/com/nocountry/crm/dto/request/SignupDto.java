package com.nocountry.crm.dto.request;

import lombok.Data;

@Data
public class SignupDto {
    String fullName;
    String email;
    String password;
    String companyCode;
}
