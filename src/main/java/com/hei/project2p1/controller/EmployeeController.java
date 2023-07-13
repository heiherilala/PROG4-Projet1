package com.hei.project2p1.controller;

import com.hei.project2p1.controller.Mapper.EmployeeMapper;
import com.hei.project2p1.controller.Mapper.EmployeeType.EmployeeUI;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
     private final EmployeeService employeeService;

    @GetMapping(value = "/")
    public String index(HttpSession session, Model model) {
        List<Employee> employees = employeeService.getEmployeesFromDB();
        List<EmployeeUI> employeeUIS = employeeMapper.toUI(employees);
        session.setAttribute("employees", employeeUIS);
        model.addAttribute("employees",employeeUIS);
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "index";
    }

    @GetMapping(value = "/add-new-employee")
    public String addNewEmplyee(HttpSession session, Model model) {
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "add-employee";
    }
    @GetMapping(value = "/employees/{id}/details")
    public String details(HttpSession session, Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeUI employeeUI = employeeMapper.toUI(employee);
        session.setAttribute("employee", employeeUI);
        model.addAttribute("employee",employeeUI);
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "employee_details";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@ModelAttribute("newEmployee") EmployeeUI employeeUI, HttpSession session) {
        employeeService.save(employeeMapper.toDomain(employeeUI));
        List<Employee> employees = employeeService.getEmployeesFromDB();
        List<EmployeeUI> employeeUIS = employeeMapper.toUI(employees);
        session.setAttribute("employees", employeeUIS);
        return "redirect:/";
    }


}
