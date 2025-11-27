package com.nocountry.crm.mapper.contact;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.dto.contact.SearchContactResultDto;
import com.nocountry.crm.dto.response.PageResponseDto;
import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.enums.FunnelStatus;
import com.nocountry.crm.mapper.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface ContactMapper {
    @Mapping(target = "funnelStatus", source = "funnelStatus", qualifiedByName = "stringToFunnelStatus")
    @Mapping(target = "active", constant = "true")
    Contact toContact(CreateContactDto createContactDto);
    @Mapping(target = "tags", source = "tags")
    SearchContactResultDto toSearchResultDto(Contact contact);
    default PageResponseDto<SearchContactResultDto> toPageResponse(Page<Contact> page) {
        List<SearchContactResultDto> content = page.getContent().stream()
                .map(this::toSearchResultDto)
                .collect(Collectors.toList());

        return new PageResponseDto<>(
                content,
                page.getNumber(),
                page.isLast(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst()
        );
    }

    @Named("stringToFunnelStatus")
    default FunnelStatus stringToFunnelStatus(String status) {
        if (status != null) {
            return FunnelStatus.valueOf(status);
        }
        return null;
    }
}
