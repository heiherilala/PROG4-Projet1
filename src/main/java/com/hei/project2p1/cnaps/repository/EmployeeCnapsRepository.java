package com.hei.project2p1.cnaps.repository;

import com.hei.project2p1.cnaps.entity.EmployeeCnapsEntity;
import com.hei.project2p1.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCnapsRepository extends JpaRepository<EmployeeCnapsEntity,String> {

    List<EmployeeCnapsEntity> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndGenderContainingIgnoreCaseAndFunctionContainingIgnoreCase(String lastName, String firstName, Employee.Gender gender, String function, Pageable pageable);

}
