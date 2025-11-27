package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.RequestFilterDto;
import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseFilterDto;
import com.nocountry.crm.dto.response.ResponseTagDto;

import java.util.List;
import java.util.UUID;

public interface IFilterService {


    ResponseFilterDto createFilter(String userEmail, RequestFilterDto request);

    ResponseFilterDto getFilterById(String userEmail, UUID id);

    List<ResponseFilterDto> getAllFiltersByCompany(String userEmail);

    ResponseFilterDto updateFilter(String userEmail, UUID filterId, RequestFilterDto request);

    void deleteFilter(String userEmail, UUID id);

}
