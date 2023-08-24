package com.hei.project2p1.service;

import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.Company;
import com.hei.project2p1.repository.firm.CompanyRepository;
import com.hei.project2p1.repository.firm.mapper.CompanyMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Transactional
    public Company getCompanyInfo(){
        return mapper.toDomain(repository.findById("1").orElseThrow(()-> new NotFoundException("Company not found")));
    }
}
