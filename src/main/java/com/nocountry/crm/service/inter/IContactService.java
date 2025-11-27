package com.nocountry.crm.service.inter;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.dto.contact.SearchContactCriteriaDto;
import com.nocountry.crm.dto.contact.SearchContactResultDto;
import com.nocountry.crm.dto.response.PageResponseDto;
import com.nocountry.crm.entity.Contact;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IContactService {
    Contact save(CreateContactDto createContactDto);
    PageResponseDto<SearchContactResultDto> contactsSearch(SearchContactCriteriaDto criteria);
}
