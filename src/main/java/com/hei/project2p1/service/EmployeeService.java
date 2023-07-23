package com.hei.project2p1.service;

import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    //TODO: pagination all get
    //TODO: one query native
    private final String REGISTRATION_PREFIX = "EMP";

    private final RegistrationNoTrackerService registrationNoTrackerService;

    private final EmployeeRepository repository;

    private final PhoneService phoneService;


    public List<Employee> getEmployeesFromDB() {
        return repository.findAll();
    }
    public Employee getEmployeeById(Integer id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee with id"+ id + "not found."));
    }

    @Transactional
    public Employee save(Employee employee, List<String> phonesNo) {
        Employee toSave = autoSetRegNo(employee);
        Employee saved = repository.save(toSave);
        List<Phone> phones = phoneService.addPhonesToEmployee(saved,phonesNo);
        saved.setPhones(phones);
        return saved;
    }

    @Transactional
    public List<Employee> saveAll(List<Employee> employees) {
        List<Employee> toSave = autoSetRegNo(employees);
        return repository.saveAll(toSave);
    }

    private Employee autoSetRegNo(Employee employee){
        if (employee.getRegistrationNo()==null){
            Long last = registrationNoTrackerService.getLastRegistrationNo();
            Long updatedNo = last + 1;
            employee.setRegistrationNo(REGISTRATION_PREFIX + (updatedNo));
            registrationNoTrackerService.updateLastNo(updatedNo);
        }
        return employee;
    }

    private List<Employee> autoSetRegNo(List<Employee> employeeList){
        Long last = registrationNoTrackerService.getLastRegistrationNo();
        for (Employee e : employeeList){
            if (e.getRegistrationNo()==null){
                e.setRegistrationNo(REGISTRATION_PREFIX + last);
                last += 1L;
            }
        }
        registrationNoTrackerService.updateLastNo(last);
        return employeeList;
    }
}
