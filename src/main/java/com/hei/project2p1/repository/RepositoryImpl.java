package com.hei.project2p1.repository;

import com.hei.project2p1.cnaps.entity.EmployeeCnapsEntity;
import com.hei.project2p1.cnaps.repository.EmployeeCnapsRepository;
import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.repository.dao.EmployeeEntityDao;
import com.hei.project2p1.repository.entity.EmployeeEntity;
import com.hei.project2p1.repository.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@AllArgsConstructor
public class RepositoryImpl implements Repository {
    private EmployeeRepository mainRepository;
    private EmployeeCnapsRepository cnapsRepository;
    private final EmployeeEntityDao employeeDao;

    private final EmployeeMapper mapper;

    @Override
    public double count() {
        return mainRepository.count();
    }

    @Override
    public Employee save(Employee toSave) {
        if (toSave.getId()!=null){
            Optional<EmployeeEntity> existingEmployee = mainRepository.findById(toSave.getId());
            existingEmployee.ifPresent(present -> toSave.setEndToEndId(present.getCnapsEndToEndId()));
        }
        EmployeeEntity entityToSave = mapper.toEntity(toSave);
        return mapper.toDomain(
                mainRepository.save(entityToSave));
    }


    @Override
    public Employee findById(String id) {

        EmployeeEntity employee = mainRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Employee with id "+id+" not found"));
        Employee toDisplay = mapper.toDomain(employee);
        return setCnapsNumber(employee.getCnapsEndToEndId(),toDisplay);
    }


    @Override
    public List<Employee> findByCriteria(String firstName, String lastName, String function, String countryCode,
                                         String gender, LocalDate entranceDateAfter, LocalDate entranceDateBefore,
                                         LocalDate leaveDateAfter, LocalDate leaveDateBefore, Pageable pageable) {
        List<EmployeeEntity> entityList = employeeDao.findByCriteria(
                firstName, lastName,function,countryCode, gender,
                entranceDateAfter, entranceDateBefore, leaveDateAfter, leaveDateBefore, pageable);
                //.stream().map(mapper::toDomain).toList();
        List<Employee> toDisplay = new ArrayList<>();
        for (EmployeeEntity e:entityList) {
            toDisplay.add(setCnapsNumber(e.getCnapsEndToEndId(),mapper.toDomain(e)));
        }
        return toDisplay;
    }

    private Employee setCnapsNumber(String employeeCnapsId, Employee employee){
        if (employeeCnapsId != null && !employeeCnapsId.isEmpty()) {
            Optional<EmployeeCnapsEntity> employeeCnaps = cnapsRepository.findById(employeeCnapsId);
            employee.setCnapsNumber(employeeCnaps.map(EmployeeCnapsEntity::getCnapsNumber).orElse(null));
        }
        return employee;
    }

}
