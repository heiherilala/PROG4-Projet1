package com.hei.project2p1.service;

import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.RegistrationNoTracker;
import com.hei.project2p1.repository.firm.RegistrationNoTrackerRepository;
import com.hei.project2p1.repository.firm.entity.RegistrationNoTrackerEntity;
import com.hei.project2p1.repository.firm.mapper.RegistrationNoTrackerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationNoTrackerService {
    private final RegistrationNoTrackerRepository repository;
    private final RegistrationNoTrackerMapper mapper;

    private final int ID = 1;

    private RegistrationNoTracker getNoTracker(){
        Long initialNo =0L;
        Optional<RegistrationNoTrackerEntity> registrationNoTracker = repository.findById(ID);
        if (registrationNoTracker.isEmpty()){
            RegistrationNoTrackerEntity noTracker= (new RegistrationNoTrackerEntity(ID,initialNo));
            repository.save(noTracker);
            registrationNoTracker = Optional.of(noTracker);
        }
        RegistrationNoTrackerEntity noTracker = registrationNoTracker.orElseThrow(()-> new NotFoundException("No registration Tracker found"));
        return mapper.toDomain(noTracker);
    }
    public Long getLastRegistrationNo(){
        RegistrationNoTracker registrationNoTracker = getNoTracker();
        return registrationNoTracker.getLastNo();
    }

    public void updateLastNo(Long last){
        RegistrationNoTracker registrationNoTracker = getNoTracker();
        registrationNoTracker.setLastNo(last);
        repository.save(mapper.toEntity(registrationNoTracker));
    }
}
