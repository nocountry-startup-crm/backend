package com.nocountry.crm.service.inter;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.entity.Contact;

public interface IContactService {
    Contact save(CreateContactDto createContactDto);
}
