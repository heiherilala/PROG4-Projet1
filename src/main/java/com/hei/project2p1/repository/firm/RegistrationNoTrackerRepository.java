package com.hei.project2p1.repository.firm;

import com.hei.project2p1.repository.firm.entity.RegistrationNoTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationNoTrackerRepository extends JpaRepository<RegistrationNoTrackerEntity,Integer> {

}
