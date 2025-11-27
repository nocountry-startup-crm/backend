package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.request.RequestFilterDto;
import com.nocountry.crm.dto.request.RequestTagDto;
import com.nocountry.crm.dto.response.ResponseFilterDto;
import com.nocountry.crm.dto.response.ResponseTagDto;
import com.nocountry.crm.entity.Filter;
import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.exception.UserNotFoundException;
import com.nocountry.crm.mapper.FilterMapper;
import com.nocountry.crm.mapper.helper.CountryMapperHelper;
import com.nocountry.crm.mapper.helper.TagMapperHelper;
import com.nocountry.crm.repository.FilterRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.IFilterService;
import com.nocountry.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements IFilterService {
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    private final FilterMapper filterMapper;
    private final UserService userService;
    private final TagMapperHelper tagMapperHelper;
    private final CountryMapperHelper countryMapperHelper;

    @Override
    public ResponseFilterDto createFilter(String userEmail, RequestFilterDto request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + userEmail));

        if (filterRepository.existsByName(request.name())) {
            throw new RuntimeException("Ya existe un filtro con ese nombre");
        }

        Filter filter = filterMapper.toEntity(request);
        filter.setCreatedUserId(user.getId());
        filter.setUpdatedUserId(user.getId());
        filter.setCompany(user.getCompany());

        Filter savedFilter = filterRepository.save(filter);
        return filterMapper.toDto(savedFilter);
    }

    @Override
    public ResponseFilterDto getFilterById(String userEmail, UUID id) {
        Filter filter = isUserValid(userEmail, id);
        return filterMapper.toDto(filter);
    }

    @Override
    public List<ResponseFilterDto> getAllFiltersByCompany(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<Filter> filters = filterRepository.findByCompanyId(user.getCompany().getId());

        return filters.stream()
                .map(filterMapper::toDto)
                .toList();
    }

    @Override
    public ResponseFilterDto updateFilter(String userEmail, UUID filterId, RequestFilterDto request) {
        User user = userService.getUserByEmail(userEmail);
        Filter filter = isUserValid(userEmail, filterId);

        if (!request.name().equals(filter.getName()) &&
                filterRepository.existsByNameAndCompanyId(request.name(), user.getCompany().getId())) {
            throw new RuntimeException("Ya existe un filtro con ese nombre");
        }

        filter.setName(request.name() != null ? request.name() : filter.getName());
        filter.setCode(request.code() != null ? request.code() : filter.getCode());
        filter.setContactCreationFrom(request.contactCreationFrom() != null ?
                request.contactCreationFrom() : filter.getContactCreationFrom());
        filter.setTags(request.tags() != null ?
                tagMapperHelper.mapTagCodesToTags(request.tags()) : filter.getTags());
        filter.setCountries(request.countries() != null ?
                countryMapperHelper.mapCountryCodesToCountries(request.countries()) : filter.getCountries());
        filter.setContactCreationTo(request.contactCreationTo() != null ?
                request.contactCreationTo() : filter.getContactCreationTo());
        filter.setUpdatedUserId(user.getId());

        Filter savedFilter = filterRepository.save(filter);
        return filterMapper.toDto(savedFilter);
    }

    @Override
    public void deleteFilter(String userEmail, UUID id) {
        Filter filter = isUserValid(userEmail, id);
        filter.setDeleted(true);
        filterRepository.save(filter);
    }

    private Filter getFilterById(UUID filterId) {
        return filterRepository.findById(filterId)
                .orElseThrow(() -> new UsernameNotFoundException("Filtro no encontrado con el id " + filterId));
    }

    private Filter isUserValid(String userEmail, UUID filterId) {
        User user = userService.getUserByEmail(userEmail);
        Filter filter = getFilterById(filterId);
        if (!Objects.equals(filter.getCompany(), user.getCompany())) throw new RuntimeException("Bad request");
        return filter;
    }
}
