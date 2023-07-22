package com.hei.project2p1.service;

import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneService {
    private final PhoneRepository repository;
    private List<Phone> getAll(){
        return repository.findAll();
    }

    private List<Phone> getByOwnerId(Integer ownerId){
        return repository.findAllByEmployeeId(ownerId);
    }
}
