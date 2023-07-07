package com.hei.project2p1.service;

import com.hei.project2p1.modele.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    public List<Employee> getEmployeesFromSession(HttpSession session) {
        List<Employee> employees = (List<Employee>) session.getAttribute("employees");
        if (employees == null) {
            employees = new ArrayList<>();
            session.setAttribute("employees", employees);
        }
        return employees;
    }
}
