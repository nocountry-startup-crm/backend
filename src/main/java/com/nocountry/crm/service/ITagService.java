package com.nocountry.crm.service;

import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface ITagService {
    ResponseTagDto createTag(String userEmail, RequestTagDto requestTagDto);
    ResponseTagDto getTagById(UUID tagId);
    List<ResponseTagDto> getAllTags();
    ResponseTagDto updateTag(String userEmail, UUID tagId, RequestTagDto requestTagDto);
    void deleteTag(UUID tagId);

    Tag findByCode(String code);
}
