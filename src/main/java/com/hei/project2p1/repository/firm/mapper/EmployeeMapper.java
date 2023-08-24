package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.repository.firm.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private final PhoneMapper phoneMapper;

    public Employee toDomain(EmployeeEntity entity) {
        return Employee.builder()
                .id(entity.getId())
                .registrationNo(entity.getRegistrationNo())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .gender(Employee.Gender.valueOf(entity.getGender().toString()))
                .phones(entity.getPhones().stream().map(phoneMapper::toDomain).toList())
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
                .endToEndId(entity.getCnapsEndToEndId())
                .build();
    }

    public EmployeeEntity toEntity(Employee domain) {
        return EmployeeEntity.builder()
                .id(domain.getId())
                .registrationNo(domain.getRegistrationNo())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .birthDate(domain.getBirthDate())
                .gender(domain.getGender()==null?null:EmployeeEntity.Gender.valueOf(domain.getGender().toString()))
                .phones(List.of())
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
                .socioProfessionalCategory(domain.getSocioProfessionalCategory()==null?null:EmployeeEntity.SocioProfessionalCategory.valueOf(domain.getSocioProfessionalCategory().toString()))
                .cnapsNumber(domain.getCnapsNumber())
                .photo(domain.getPhoto())
                .cnapsEndToEndId(domain.getEndToEndId())
                .build();
    }

}

