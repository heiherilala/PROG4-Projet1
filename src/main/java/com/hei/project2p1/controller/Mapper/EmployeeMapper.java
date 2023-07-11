package com.hei.project2p1.controller.Mapper;

import com.hei.project2p1.controller.Mapper.EmployeeType.EmployeeUI;
import com.hei.project2p1.modele.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {
    public Employee toDomain(EmployeeUI employeeUI){
        return Employee.builder()
                .id(employeeUI.getId()==null?null:Integer.parseInt(employeeUI.getId()))
                .lastName(employeeUI.getLastName())
                .firstName(employeeUI.getFirstName())
                .registrationNo(employeeUI.getRegistrationNo())
                .birthDate(LocalDate.parse(employeeUI.getBirthDate()))
                .photo(employeeUI.getPhoto())
                .build();
    }

    public List<Employee> toDomain(List<EmployeeUI> employeeUIs){
        List<Employee> employees = new ArrayList<>();
        for (EmployeeUI employeeUI: employeeUIs){
            employees.add(toDomain(employeeUI));
        }
        return employees;
    }

    public EmployeeUI toUI(Employee employee){
        return EmployeeUI.builder()
                .id(String.valueOf(employee.getId()))
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(String.valueOf(employee.getBirthDate()))
                .registrationNo(employee.getRegistrationNo())
                .photo(employee.getPhoto())
                .build();
    }

    public List<EmployeeUI> toUI(List<Employee> employees){
        List<EmployeeUI> employeeUIS = new ArrayList<>();
        for (Employee employee: employees){
            employeeUIS.add(toUI(employee));
        }
        return employeeUIS;
    }
}

