package com.hei.project2p1.repository;

import com.hei.project2p1.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Integer> {
    List<Phone> findAllByEmployeeId(String OwnerId);

    @Query(value = "SELECT * FROM phone p where p.employee_id = ?1",nativeQuery = true)
    public List<Phone> getPhoneByOwnerId(String OwnerId);

    List<Phone> findAllByCompanyId(String ownerId);
}
