package com.hei.project2p1.service;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final String REGISTRATIONPREFIX = "EMP";

    private final RegistrationNoTrackerService registrationNoTrackerService;

    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesFromSession(HttpSession session) {
        List<Employee> employees = (List<Employee>) session.getAttribute("employees");
        if (employees == null) {
            employees = new ArrayList<>();
            session.setAttribute("employees", employees);
        }
        return employees;
    }

    public List<Employee> getEmployeesFromDB() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee save(Employee employee) {
        Long last = registrationNoTrackerService.getLastRegistrationNo();
        Long updatedNo = last + 1;
        employee.setRegistrationNo(REGISTRATIONPREFIX + (updatedNo));
        registrationNoTrackerService.updateLastNo(updatedNo);
        return employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> saveAll(List<Employee> employees) {
        Long last = registrationNoTrackerService.getLastRegistrationNo();
        for (Employee e : employees) {
            e.setRegistrationNo(REGISTRATIONPREFIX + last);
            last += 1L;
        }
        registrationNoTrackerService.updateLastNo(last);
        return employeeRepository.saveAll(employees);
    }
}
