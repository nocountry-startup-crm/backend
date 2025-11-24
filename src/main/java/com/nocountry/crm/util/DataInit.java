package com.nocountry.crm.util;

import com.nocountry.crm.entity.Company;
import com.nocountry.crm.entity.User;
import com.nocountry.crm.entity.enums.RoleCode;
import com.nocountry.crm.repository.ICompanyRepository;
import com.nocountry.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {
    private final ICompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICompanyRepository iCompanyRepository;

    @Override
    public void run(String... args) {
        if (companyRepository.count() == 0) {
            insertCompanies();
        }
        if (userRepository.count() == 0) {
            insertUsers();
        }
    }

    void insertCompanies() {
        Company companyCrm = new Company();
        companyCrm.setName("Company CRM");
        companyCrm.setCode("777");
        iCompanyRepository.save(companyCrm);
    }

    void insertUsers() {
        User customer_admin = new User();
        customer_admin.setFullName("customer customer_admin");
        customer_admin.setEmail("customer_admin@example.com");
        customer_admin.setPassword(passwordEncoder.encode("CustomerAdmin12345678"));
        customer_admin.setRole(RoleCode.CUSTOMER_ADMIN);
        Company companyCrm = companyRepository.findByCode("777").get();
        customer_admin.setCompany(companyCrm);
        userRepository.save(customer_admin);
    }
}