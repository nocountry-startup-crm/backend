package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.exception.FunctionalException;
import com.nocountry.crm.mapper.TagMapper;
import com.nocountry.crm.repository.ITagRepository;
import com.nocountry.crm.service.ITagService;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService {
    private final ITagRepository tagRepository;
    private final UserService userService;
    private final TagMapper tagMapper;

    @Override
    public ResponseTagDto createTag(String userEmail, RequestTagDto requestTagDto) {
        User user = userService.getUserByEmail(userEmail);

        if (tagRepository.existsByName(requestTagDto.getName())) {
            throw new FunctionalException("There is already a tag with the same name.", HttpStatus.CONFLICT);
        }

        Tag tag = tagMapper.toEntity(requestTagDto);
        tag.setCreatedUserId(user.getId());
        tag.setUpdatedUserId(user.getId());
        tag.setCompany(user.getCompany());

        Tag savedtag = tagRepository.save(tag);
        return tagMapper.toResponse(savedtag);
    }

    @Override
    public ResponseTagDto getTagById(UUID tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new FunctionalException("Tag not found.", HttpStatus.NOT_FOUND));
        return tagMapper.toResponse(tag);
    }

    @Override
    public List<ResponseTagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    @Override
    public ResponseTagDto updateTag(String userEmail, UUID tagId, RequestTagDto requestTagDto) {
        User user = userService.getUserByEmail(userEmail);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new FunctionalException("Tag not found.", HttpStatus.NOT_FOUND));

        if (!requestTagDto.getName().equals(tag.getName()) &&
                tagRepository.existsByName(requestTagDto.getName())) {
            throw new FunctionalException("There is already a tag with the same name.", HttpStatus.CONFLICT);
        }
        // validar por company tambien?

        tag.setName(requestTagDto.getName() != null ? requestTagDto.getName() : tag.getName());
        tag.setCode(requestTagDto.getCode() != null ? requestTagDto.getCode() : tag.getCode());
        tag.setColor(requestTagDto.getColor() != null ? requestTagDto.getColor() : tag.getColor());
        tag.setUpdatedUserId(user.getId());

        Tag savedtag = tagRepository.save(tag);
        System.out.println(savedtag);
        return tagMapper.toResponse(savedtag);
    }

    @Override
    public void deleteTag(UUID tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new FunctionalException("Tag not found.", HttpStatus.NOT_FOUND);
        }
        tagRepository.deleteById(tagId);
    }

    @Override
    public Tag findByCode(String code) {
        return tagRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado con código " + code));
    }
}
