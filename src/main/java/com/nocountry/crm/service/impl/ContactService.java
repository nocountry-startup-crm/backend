package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.dto.contact.SearchContactCriteriaDto;
import com.nocountry.crm.dto.contact.SearchContactResultDto;
import com.nocountry.crm.dto.response.PageResponseDto;
import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.exception.FunctionalException;
import com.nocountry.crm.mapper.contact.ContactMapper;
import com.nocountry.crm.repository.ContactRepository;
import com.nocountry.crm.repository.ITagRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.inter.IContactService;
import com.nocountry.crm.specification.ContactSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {

    @Autowired
    private final ContactMapper contactMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ContactRepository contactRepository;

    @Autowired
    private final ITagRepository tagRepository;

    @Override
    public Contact save(CreateContactDto createContactDto) {
        if (contactRepository.existsByEmail(createContactDto.email())) {
            throw new FunctionalException("Email is already registered. Please use a different email address.", HttpStatus.CONFLICT);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            throw new FunctionalException("You must be authenticated to perform this action.", HttpStatus.UNAUTHORIZED);

        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        if(user.isEmpty()) {
            throw new FunctionalException("User not found. Please ensure the user exists in the system.", HttpStatus.NOT_FOUND);
        }

        try {
            Contact contact = contactMapper.toContact(createContactDto);
            contact.setUser(user.get());
            contact.setCompany(user.get().getCompany());
            contact.setCreatedUserId(user.get().getId());
            if(!createContactDto.tagIds().isEmpty()){
                Set<Tag> existingTags = new HashSet<>(tagRepository.findAllById(createContactDto.tagIds()));

                contact.setTags(existingTags);
            }
            return contactRepository.save(contact);
        } catch (Exception e) {
            String errorMessage = "Error al guardar el contacto: " + e.getMessage();
            throw new RuntimeException(errorMessage, e);
        }
    }

    public PageResponseDto<SearchContactResultDto> contactsSearch(SearchContactCriteriaDto criteria){
        Pageable pageable = PageRequest.of(criteria.page(), criteria.size());

        Specification<Contact> spec = Specification.unrestricted();

        if(criteria.tagCodes() != null && !criteria.tagCodes().isEmpty()){
            spec = spec.and(ContactSpecification.hasTags(criteria.tagCodes()));
        }

        if(criteria.fullName() != null && !criteria.fullName().isEmpty() && !criteria.fullName().isBlank()){
            spec = spec.and(ContactSpecification.hasName(criteria.fullName()));
        }

        if(criteria.email() != null && !criteria.email().isEmpty() && !criteria.email().isBlank()){
            spec = spec.and(ContactSpecification.hasEmail(criteria.email()));
        }

        if(criteria.dateFrom() != null){
            spec = spec.and(ContactSpecification.createdAtFrom(criteria.dateFrom()));
        }

        if(criteria.dateTo() != null){
            spec = spec.and(ContactSpecification.createdAtTo(criteria.dateTo()));
        }

        if(criteria.countryId() != null){
            spec = spec.and(ContactSpecification.hasCountryId(criteria.countryId()));
        }

        if(criteria.active() != null){
            spec = spec.and(ContactSpecification.isActive(criteria.active()));
        }

        Page<Contact> contactPage = contactRepository.findAll(spec, pageable);

        return contactMapper.toPageResponse(contactPage);
    }
}
