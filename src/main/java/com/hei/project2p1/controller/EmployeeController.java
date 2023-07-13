package com.hei.project2p1.controller;

import com.hei.project2p1.controller.Mapper.EmployeeMapper;
import com.hei.project2p1.controller.Mapper.EmployeeType.CreateEmployeeUI;
import com.hei.project2p1.controller.Mapper.EmployeeType.EmployeeUI;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
     private final EmployeeService employeeService;

    @GetMapping(value = "/")
    public String index(HttpSession session, Model model) {
        List<Employee> employees = employeeService.getEmployeesFromDB();
        List<EmployeeUI> createEmployeeUIS = employeeMapper.toUI(employees);
        session.setAttribute("employees", createEmployeeUIS);
        model.addAttribute("employees", createEmployeeUIS);
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "index";
    }

    @GetMapping(value = "/add-new-employee")
    public String addNewEmployee(HttpSession session, Model model ) {
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "add-employee";
    }
    @GetMapping(value = "/modify-employee")
    public String modifyEmployeePage(HttpSession session, Model model, @ModelAttribute("newEmployee") EmployeeUI employeeUI) {
        model.addAttribute("newEmployee",employeeUI);
        return "add-employee";
    }
    @GetMapping(value = "/employees/{id}/details")
    public String details(HttpSession session, Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeUI createEmployeeUI = employeeMapper.toUI(employee);
        session.setAttribute("employee", createEmployeeUI);
        model.addAttribute("employee", createEmployeeUI);
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "employee_details";
    }

    @PostMapping("/submitEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") CreateEmployeeUI createEmployeeUI, HttpSession session) throws IOException {
        employeeService.save(employeeMapper.toDomain(createEmployeeUI));
        List<Employee> employees = employeeService.getEmployeesFromDB();
        List<EmployeeUI> createEmployeeUIS = employeeMapper.toUI(employees);
        session.setAttribute("employees", createEmployeeUIS);
        return "redirect:/";
    }

    @PostMapping("/modifyEmployee")
    public String modifyEmployee(@RequestParam("employeeId") String employeeIndex, Model model) {
            Employee employee = employeeService.getEmployeeById(employeeIndex);
            model.addAttribute("newEmployee", employeeMapper.toUI(employee));
        return "redirect:/modify-employee";
    }
}
