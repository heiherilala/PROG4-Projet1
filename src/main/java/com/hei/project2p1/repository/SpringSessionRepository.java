package com.hei.project2p1.repository;

import com.hei.project2p1.model.SpringSession;
import com.hei.project2p1.repository.entity.SpringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringSessionRepository extends JpaRepository<SpringSessionEntity,String> {
    List<SpringSession> findAllByPrincipalName(String name);

    SpringSession findBySessionId(String id);

}
