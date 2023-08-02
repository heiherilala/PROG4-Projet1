package com.hei.project2p1.service;

import com.hei.project2p1.model.SpringSession;
import com.hei.project2p1.repository.SpringSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpringSessionService {
    private final SpringSessionRepository repository;

    public void shutUpSessionById(String id){
        //List<SpringSession> toDelete = repository.findAllByPrincipalName(connected);
        //repository.deleteAll(toDelete);
        String primaryId = repository.findBySessionId(id).getPrimaryId();
        repository.deleteById(primaryId);
    }

    public SpringSession getBySessionId(String id){
        return repository.findBySessionId(id);
    }

    public List<SpringSession> getAll(){
        return repository.findAll();
    }

}
