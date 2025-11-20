package com.nocountry.crm.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDto {
    private String token;
}
