package com.hei.project2p1.repository.mapper;

import com.hei.project2p1.model.Company;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.entity.CompanyEntity;
import com.hei.project2p1.repository.entity.EmployeeEntity;
import com.hei.project2p1.repository.entity.PhoneEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyAndEmployeeMapper {
    public Employee toDomain(EmployeeEntity entity) {
        return Employee.builder()
                .id(entity.getId())
                .registrationNo(entity.getRegistrationNo())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .gender(Employee.Gender.valueOf(entity.getGender().toString()))
                .phones(entity.getPhones().stream().map(this::toDomain).toList())
                .address(entity.getAddress())
                .personalEmail(entity.getPersonalEmail())
                .professionalEmail(entity.getProfessionalEmail())
                .cinNumber(entity.getCinNumber())
                .cinIssueDate(entity.getCinIssueDate())
                .cinIssuePlace(entity.getCinIssuePlace())
                .function(entity.getFunction())
                .numberOfChildren(entity.getNumberOfChildren())
                .hiringDate(entity.getHiringDate())
                .departureDate(entity.getDepartureDate())
                .socioProfessionalCategory(Employee.SocioProfessionalCategory.valueOf(entity.getSocioProfessionalCategory().toString()))
                .cnapsNumber(entity.getCnapsNumber())
                .photo(entity.getPhoto())
                .build();
    }

    public EmployeeEntity toEntity(Employee domain) {
        return EmployeeEntity.builder()
                .id(domain.getId())
                .registrationNo(domain.getRegistrationNo())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .birthDate(domain.getBirthDate())
                .gender(EmployeeEntity.Gender.valueOf(domain.getGender().toString()))
                .phones(domain.getPhones().stream().map(this::toEntity).toList())
                .address(domain.getAddress())
                .personalEmail(domain.getPersonalEmail())
                .professionalEmail(domain.getProfessionalEmail())
                .cinNumber(domain.getCinNumber())
                .cinIssueDate(domain.getCinIssueDate())
                .cinIssuePlace(domain.getCinIssuePlace())
                .function(domain.getFunction())
                .numberOfChildren(domain.getNumberOfChildren())
                .hiringDate(domain.getHiringDate())
                .departureDate(domain.getDepartureDate())
                .socioProfessionalCategory(EmployeeEntity.SocioProfessionalCategory.valueOf(domain.getSocioProfessionalCategory().toString()))
                .cnapsNumber(domain.getCnapsNumber())
                .photo(domain.getPhoto())
                .build();
    }

    public Company toDomain(CompanyEntity entity) {
        return Company.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .companyDescription(entity.getCompanyDescription())
                .slogan(entity.getSlogan())
                .address(entity.getAddress())
                .contactEmail(entity.getContactEmail())
                .phones(entity.getPhones().stream().map(this::toDomain).toList())
                .logo(entity.getLogo())
                .nif(entity.getNif())
                .stat(entity.getStat())
                .rcs(entity.getRcs())
                .build();
    }

    public CompanyEntity toEntity(Company domain) {
        return CompanyEntity.builder()
                .id(domain.getId())
                .companyName(domain.getCompanyName())
                .companyDescription(domain.getCompanyDescription())
                .slogan(domain.getSlogan())
                .address(domain.getAddress())
                .contactEmail(domain.getContactEmail())
                .phones(domain.getPhones().stream().map(this::toEntity).toList())
                .logo(domain.getLogo())
                .nif(domain.getNif())
                .stat(domain.getStat())
                .rcs(domain.getRcs())
                .build();
    }

    public Phone toDomain(PhoneEntity entity) {
        return Phone.builder()
                .id(entity.getId())
                .number(entity.getNumber())
                .countryCode(entity.getCountryCode())
                .employee(toDomain(entity.getEmployee()))
                .company(toDomain(entity.getCompany()))
                .build();
    }

    public PhoneEntity toEntity(Phone domain) {
        return PhoneEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .countryCode(domain.getCountryCode())
                .employee(toEntity(domain.getEmployee()))
                .company(toEntity(domain.getCompany()))
                .build();

    }
}

