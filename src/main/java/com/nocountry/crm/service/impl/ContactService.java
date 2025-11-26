package com.nocountry.crm.service.impl;

import com.nocountry.crm.dto.contact.CreateContactDto;
import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.exception.FunctionalException;
import com.nocountry.crm.mapper.contact.ContactMapper;
import com.nocountry.crm.repository.ContactRepository;
import com.nocountry.crm.repository.UserRepository;
import com.nocountry.crm.service.inter.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {

    @Autowired
    private final ContactMapper contactMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ContactRepository contactRepository;

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

        Contact contact = contactMapper.toContact(createContactDto);

        contact.setUser(user.get());

        return null;
    }
}
