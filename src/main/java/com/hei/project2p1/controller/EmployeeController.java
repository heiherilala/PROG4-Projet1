package com.hei.project2p1.controller;

import com.hei.project2p1.controller.Mapper.EmployeeMapper;
import com.hei.project2p1.controller.Mapper.EmployeeType.CreateEmployeeUI;
import com.hei.project2p1.controller.Mapper.EmployeeType.EmployeeUI;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

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
    public String addNewEmployee(HttpSession session, Model model) {
        model.addAttribute("newEmployee", employeeMapper.toUI(new Employee()));
        return "add-employee";
    }

    @GetMapping(value = "/employees/{id}/edit")
    public String modifyEmployeePage(HttpSession session, Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        CreateEmployeeUI createEmployeeUI = CreateEmployeeUI.builder().build();
        //session.setAttribute("EmployeeToModify", createEmployeeUI);
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("regNo", employee.getRegistrationNo());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("lastName", employee.getLastName());
        model.addAttribute("birthDate", employee.getBirthDate());
        model.addAttribute("photoString", employee.getPhoto());
        return "modify-employee";
    }

    @GetMapping(value = "/employees/{id}/details")
    public String details(HttpSession session, Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeUI createEmployeeUI = employeeMapper.toUI(employee);
        session.setAttribute("employee", createEmployeeUI);
        model.addAttribute("employee", createEmployeeUI);
        return "employee_details";
    }

    @PostMapping("/submitEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") CreateEmployeeUI createEmployeeUI, HttpSession session) throws IOException {
        employeeService.save(employeeMapper.toDomain(createEmployeeUI));
        return "redirect:/";
    }

    @PostMapping("/modifyEmployee")
    public String mofidyEmployee(
            HttpSession session,
            @RequestParam("id") String id,
            @ModelAttribute("employeeId") String employeeId,
            @ModelAttribute("firstName") String firstName,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("photoString") String photoString,
            @ModelAttribute("birthDate") String birthDate,
            @ModelAttribute("regNo") String regNo,
            @RequestParam("photo") MultipartFile photoFile) throws IOException {

        EmployeeUI createEmployeeUI = EmployeeUI.builder()
                .id(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .registrationNo(regNo)
                .birthDate(String.valueOf(birthDate))
                .photo(photoFile.getOriginalFilename().isEmpty() ? photoString : employeeMapper.MultipartImageToString(photoFile))
                .build();

        logger.info("Update photo: " + employeeMapper.MultipartImageToString(photoFile));
        logger.info("Update photo: " + createEmployeeUI.getPhoto());
        logger.info("id param : " + id);
        logger.info("id : " + employeeId);
        logger.info("fname : " + firstName);
        logger.info("lname : " + lastName);
        logger.info("bdate : " + birthDate);
        logger.info("regNo : " + regNo);
        // logger.info("Photo String : "+ photoString);
        logger.info("Photo File : " + (photoFile.getOriginalFilename()));

        employeeService.save(employeeMapper.toDomain(createEmployeeUI));
        return "redirect:/";
    }
}