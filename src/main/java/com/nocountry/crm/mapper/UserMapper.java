package com.nocountry.crm.mapper;

import com.nocountry.crm.dto.request.RequestUserDto;
import com.nocountry.crm.dto.response.ResponseUserDto;
import com.nocountry.crm.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RequestUserDto dto);
    ResponseUserDto toResponse(User user);

}
