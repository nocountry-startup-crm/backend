package com.nocountry.crm.mapper;

import com.nocountry.crm.dto.request.RequestFilterDto;
import com.nocountry.crm.dto.response.ResponseFilterDto;
import com.nocountry.crm.entity.Filter;
import com.nocountry.crm.mapper.helper.CountryMapperHelper;
import com.nocountry.crm.mapper.helper.TagMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { TagMapperHelper.class, CountryMapperHelper.class }
)
public interface FilterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "countries", source = "countries")
    Filter toEntity(RequestFilterDto dto);

    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "countries", source = "countries")
    ResponseFilterDto toDto(Filter filter);
}
