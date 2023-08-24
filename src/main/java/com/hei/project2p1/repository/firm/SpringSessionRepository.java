package com.hei.project2p1.repository.firm;

import com.hei.project2p1.repository.firm.entity.SpringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringSessionRepository extends JpaRepository<SpringSessionEntity,String> {
    List<SpringSessionEntity> findAllByPrincipalName(String name);

    SpringSessionEntity findBySessionId(String id);

}
