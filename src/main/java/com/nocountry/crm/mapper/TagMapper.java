package com.nocountry.crm.mapper;

import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toEntity(RequestTagDto dto);
    ResponseTagDto toResponse(Tag tag);
}
