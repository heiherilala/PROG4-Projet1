package com.hei.project2p1.repository.cnaps;

import com.hei.project2p1.repository.cnaps.entity.PhoneCnapsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneCnapsRepository extends JpaRepository<PhoneCnapsEntity,Integer> {
    List<PhoneCnapsEntity> findAllByEmployeeId(String OwnerId);

    @Query(value = "SELECT * FROM phone p where p.employee_id = ?1",nativeQuery = true)
    public List<PhoneCnapsEntity> getPhoneByOwnerId(String OwnerId);

    List<PhoneCnapsEntity> findAllByCompanyId(String ownerId);

    List<PhoneCnapsEntity> findAllByCountryCodeAndNumber(String code, String number);
}
