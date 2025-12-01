package com.nocountry.crm.service;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.dto.contact.SearchContactCriteriaDto;
import com.nocountry.crm.dto.contact.SearchContactResultDto;
import com.nocountry.crm.dto.response.PageResponseDto;
import com.nocountry.crm.entity.Contact;

public interface IContactService {
    Contact save(CreateContactDto createContactDto);
    PageResponseDto<SearchContactResultDto> contactsSearch(SearchContactCriteriaDto criteria);
}
