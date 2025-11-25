package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.TagColor;
import com.nocountry.crm.mapper.TagMapper;
import com.nocountry.crm.mapper.UserMapper;
import com.nocountry.crm.repository.ITagRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService {
    private final ITagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    @Override
    public ResponseTagDto createTag(String userEmail, RequestTagDto requestTagDto) {
        User user = getUserByEmail(userEmail);

        // Validar que no exista un tag con el mismo nombre
        if (tagRepository.existsByNameAndCreatedUserId(requestTagDto.getName(), user.getCreatedUserId())) {
            throw new RuntimeException("Ya existe un tag con ese nombre");
        }

        Tag tag = tagMapper.toEntity(requestTagDto);
        tag.setCreatedUserId(user.getId());
        tag.setUpdatedUserId(user.getId());
        tag.setCompany(user.getCompany());

        Tag savedtag = tagRepository.save(tag);
        return tagMapper.toResponse(savedtag);
    }

    @Override
    public ResponseTagDto getTagById(String userEmail, UUID tagId) {
        Tag tag = isUserValid(userEmail, tagId);
        return tagMapper.toResponse(tag);
    }

    @Override
    public List<ResponseTagDto> getAllTagsByUser(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<Tag> tags = tagRepository.findByCreatedUserId(user.getId());

        return tags.stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    @Override
    public ResponseTagDto updateTag(String userEmail, UUID tagId, RequestTagDto requestTagDto) {
        User user = getUserByEmail(userEmail);
        Tag tag = isUserValid(userEmail, tagId);

        // Validar que no exista un tag con el mismo nombre
        if (!requestTagDto.getName().equals(tag.getName()) &&
                tagRepository.existsByNameAndCreatedUserId(requestTagDto.getName(), user.getCreatedUserId())) {
            throw new RuntimeException("Ya existe un tag con ese nombre");
        }
        // validar por company tambien?

        tag.setName(requestTagDto.getName() != null ? requestTagDto.getName() : tag.getName());
        tag.setCode(requestTagDto.getCode() != null ? requestTagDto.getCode() : tag.getCode());
        tag.setColor(requestTagDto.getColor() != null ? requestTagDto.getColor() : tag.getColor());
        tag.setUpdatedUserId(user.getId());

        System.out.println(tag);


        Tag savedtag = tagRepository.save(tag);
        System.out.println(savedtag);
        return tagMapper.toResponse(savedtag);
    }

    @Override
    public void deleteTag(String userEmail, UUID tagId) {
        isUserValid(userEmail, tagId);
        tagRepository.deleteById(tagId);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    private Tag isUserValid(String userEmail, UUID tagId) {
        User user = getUserByEmail(userEmail);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
        if (!Objects.equals(tag.getCreatedUserId(), user.getId())) throw new RuntimeException("Bad request");
        return tag;
    }
}
