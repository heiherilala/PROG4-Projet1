package com.hei.project2p1.repository.firm;

import com.hei.project2p1.repository.firm.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity,Integer> {
    List<PhoneEntity> findAllByEmployeeId(String OwnerId);

    @Query(value = "SELECT * FROM phone p where (p.employee_id = ?1 or p.company_id = ?1)", nativeQuery = true)
    public List<PhoneEntity> getPhoneByOwnerId(String OwnerId);

    List<PhoneEntity> findAllByCompanyId(String ownerId);

    List<PhoneEntity> findAllByCountryCodeAndNumber(String code, String number);
}
