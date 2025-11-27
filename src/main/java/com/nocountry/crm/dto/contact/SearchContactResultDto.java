package com.nocountry.crm.dto.contact;

import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.enums.FunnelStatus;

import java.util.Set;
import java.util.UUID;

public record SearchContactResultDto (
    UUID id,
    String fullName,
    String email,
    String phone,
    UUID countryId,
    FunnelStatus funnelStatus,
    boolean active,
    Set<ResponseTagDto> tags
){}
