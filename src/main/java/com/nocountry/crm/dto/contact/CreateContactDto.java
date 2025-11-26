package com.nocountry.crm.dto.contact;

import java.util.UUID;
public record CreateContactDto(
    String fullName,
    String email,
    String phone,
    UUID countryId,
    String funnelStatus
){}
