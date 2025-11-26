package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseFilterDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.service.IFilterService;

import java.util.List;
import java.util.UUID;

public class FilterServiceImpl implements IFilterService {

    @Override
    public ResponseFilterDto createFilter(String userEmail, RequestTagDto request) {
        return null;
    }

    @Override
    public ResponseFilterDto getFilterById(String userEmail, UUID id) {
        return null;
    }

    @Override
    public List<ResponseFilterDto> getAllFiltersByUser(String userEmail) {
        return List.of();
    }

    @Override
    public ResponseFilterDto updateFilter(String userEmail, UUID id, RequestTagDto request) {
        return null;
    }

    @Override
    public void deleteFilter(String userEmail, UUID id) {

    }
}
