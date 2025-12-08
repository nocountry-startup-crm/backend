package com.nocountry.crm.controller;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.dto.contact.SearchContactCriteriaDto;
import com.nocountry.crm.dto.contact.SearchContactResultDto;
import com.nocountry.crm.dto.response.PageResponseDto;
import com.nocountry.crm.dto.response.ResponseDto;
import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.service.impl.ContactService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.nocountry.crm.common.ApiPaths.CONTACT_BASE;


@RequiredArgsConstructor
@RestController
@RequestMapping(CONTACT_BASE)
public class ContactController {

    @Autowired
    private final ContactService contactService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseDto<Contact> createContact(@RequestBody CreateContactDto contact) {
        Contact contactRes = contactService.save(contact);
        if(contactRes == null) return new ResponseDto<>(null, HttpStatus.INTERNAL_SERVER_ERROR, 1);

        //return new ResponseDto<>(null, HttpStatus.OK, 0);
        return new ResponseDto<>(contactRes, HttpStatus.OK, 0);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseDto<PageResponseDto<SearchContactResultDto>> searchContact(SearchContactCriteriaDto criteria) {
        PageResponseDto<SearchContactResultDto> contacts = contactService.contactsSearch(criteria);

        return new ResponseDto<>(contacts, HttpStatus.OK, 0);
    }
}
