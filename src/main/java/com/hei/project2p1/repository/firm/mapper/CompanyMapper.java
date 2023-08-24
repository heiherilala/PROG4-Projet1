package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.Company;
import com.hei.project2p1.repository.firm.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CompanyMapper {
    private final PhoneMapper phoneMapper;


    public Company toDomain(CompanyEntity entity) {
        return Company.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .companyDescription(entity.getCompanyDescription())
                .slogan(entity.getSlogan())
                .address(entity.getAddress())
                .contactEmail(entity.getContactEmail())
                .phones(entity.getPhones().stream().map(phoneMapper::toDomain).toList())
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
                .phones(List.of())
                .logo(domain.getLogo())
                .nif(domain.getNif())
                .stat(domain.getStat())
                .rcs(domain.getRcs())
                .build();
    }

}

