package com.hei.project2p1.repository.cnaps;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.repository.cnaps.entity.EmployeeCnapsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCnapsRepository extends JpaRepository<EmployeeCnapsEntity,String> {

    List<EmployeeCnapsEntity> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndGenderContainingIgnoreCaseAndFunctionContainingIgnoreCase(String lastName, String firstName, Employee.Gender gender, String function, Pageable pageable);

}
