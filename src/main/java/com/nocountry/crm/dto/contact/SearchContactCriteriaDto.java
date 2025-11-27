package com.nocountry.crm.dto.contact;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SearchContactCriteriaDto(
    List<String> tagCodes,
    String fullName,
    String email,
    LocalDateTime dateFrom,
    LocalDateTime dateTo,
    UUID countryId,
    Boolean active,
    int page,
    int size
) {}
