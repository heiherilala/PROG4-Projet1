package com.hei.project2p1.service;

import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.Company;
import com.hei.project2p1.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    public List<Company> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Company getCompanyInfo(){
        return repository.findById("1").orElseThrow(()-> new NotFoundException("Company not found"));
    }
}
