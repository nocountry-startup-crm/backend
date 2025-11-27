package com.nocountry.crm.specification;

import com.nocountry.crm.entity.Contact;
import com.nocountry.crm.entity.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ContactSpecification {
    public static Specification<Contact> hasTags(List<String> tagCodes){

        return (root, query, criteriaBuilder) -> {
            if(tagCodes == null || tagCodes.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<Contact, Tag> contactTagJoin = root.join("tags", JoinType.INNER);
            return contactTagJoin.get("code").in(tagCodes);
        };
    }

    public static Specification<Contact> hasName(String fullName){
        return (root, query, criteriaBuilder) -> {
            if(fullName == null || fullName.isEmpty() || fullName.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%"+fullName.toLowerCase()+"%");
        };
    }

    public static Specification<Contact> hasEmail(String email){
        return (root, query, criteriaBuilder) -> {
            if(email == null || email.isEmpty() || email.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%"+email.toLowerCase()+"%");
        };
    }

    public static Specification<Contact> createdAtFrom(LocalDateTime dateFrom) {
        return (root, query, criteriaBuilder) -> {
            if( dateFrom == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("createdAt"), dateFrom);
        };
    }

    public static Specification<Contact> createdAtTo(LocalDateTime dateTo){
        return (root, query, criteriaBuilder) -> {
            if( dateTo == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), dateTo);
        };
    }

    public static Specification<Contact> hasCountryId(UUID countryId) {
        return (root, query, criteriaBuilder) -> {
            if( countryId == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("countryId"), countryId);
        };
    }

    public static Specification<Contact> isActive(Boolean active) {
        return (root, query, criteriaBuilder) -> {
            if(active == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("active"), active);
        };
    }
}
