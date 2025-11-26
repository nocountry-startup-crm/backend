package com.nocountry.crm.mapper.contact;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.enums.FunnelStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "funnelStatus", source = "funnelStatus", qualifiedByName = "stringToFunnelStatus")
    @Mapping(target = "active", constant = "true")
    Contact toContact(CreateContactDto createContactDto);
    @Named("stringToFunnelStatus")
    default FunnelStatus stringToFunnelStatus(String status) {
        if (status != null) {
            return FunnelStatus.valueOf(status);
        }
        return null;
    }
}
