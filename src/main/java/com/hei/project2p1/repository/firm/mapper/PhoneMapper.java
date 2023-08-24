package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.firm.CompanyRepository;
import com.hei.project2p1.repository.firm.EmployeeRepository;
import com.hei.project2p1.repository.firm.entity.PhoneEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneMapper {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    public Phone toDomain(PhoneEntity entity) {
        return Phone.builder()
                .id(entity.getId())
                .number(entity.getNumber())
                .countryCode(entity.getCountryCode())
                .employeeId(entity.getEmployee()==null?null:entity.getEmployee().getId())
                .companyId(entity.getCompany()==null?null:entity.getCompany().getId())
                .build();
    }

    public PhoneEntity toEntity(Phone domain) {
        return PhoneEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .countryCode(domain.getCountryCode())
                .employee(domain.getEmployeeId()==null || domain.getEmployeeId().isEmpty()?
                        null : employeeRepository.findById(domain.getEmployeeId()).orElse(null))
                .company(domain.getCompanyId()==null || domain.getEmployeeId().isEmpty()?
                        null : companyRepository.findById(domain.getCompanyId()).orElse(null))
                .build();

    }
}
