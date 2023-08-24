package com.hei.project2p1.repository.firm;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.repository.firm.entity.EmployeeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String> {

    List<EmployeeEntity> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndGenderContainingIgnoreCaseAndFunctionContainingIgnoreCase(String lastName, String firstName, Employee.Gender gender, String function, Pageable pageable);

}
