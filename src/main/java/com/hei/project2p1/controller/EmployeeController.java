package com.hei.project2p1.controller;

import com.hei.project2p1.controller.constant.Url;
import com.hei.project2p1.controller.mapper.EmployeeMapper;
import com.hei.project2p1.controller.mapper.employeeType.CreateEmployeeView;
import com.hei.project2p1.controller.mapper.employeeType.EmployeeView;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(value = Url.EMPLOYEES_LIST)
    public String index( Model model) {
        List<Employee> employees = employeeService.getEmployeesFromDB();
        List<EmployeeView> createEmployeeViews = employeeMapper.toView(employees);
        model.addAttribute("employees", createEmployeeViews);
        model.addAttribute("newEmployee", employeeMapper.toView(new Employee()));
        return "index";
    }

    @GetMapping(value = Url.EMPLOYEES_ADD)
    public String addNewEmployee( Model model) {
        model.addAttribute("newEmployee", employeeMapper.toView(new Employee()));
        return "add-employee";
    }

    @GetMapping(value = Url.EMPLOYEES_UPDATE)
    public String modifyEmployeePage( Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        CreateEmployeeView createEmployeeView = CreateEmployeeView.builder().build();
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("regNo", employee.getRegistrationNo());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("lastName", employee.getLastName());
        model.addAttribute("birthDate", employee.getBirthDate());
        model.addAttribute("photoString", employee.getPhoto());
        return "modify-employee";
    }

    @GetMapping(value = Url.EMPLOYEES_DETAILS)
    public String details(HttpSession session, Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeView createEmployeeView = employeeMapper.toView(employee);
        session.setAttribute("employee", createEmployeeView);
        model.addAttribute("employee", createEmployeeView);
        return "employee_details";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") CreateEmployeeView createEmployeeView, Model model) {
        employeeService.save(employeeMapper.toDomain(createEmployeeView));
        return "redirect:"+Url.EMPLOYEES_LIST;
    }

    @PostMapping("/modifyEmployee")
    public String modifyEmployee(
            @ModelAttribute("employeeId") String employeeId,
            @ModelAttribute("firstName") String firstName,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("photoString") String photoString,
            @ModelAttribute("birthDate") String birthDate,
            @ModelAttribute("regNo") String regNo,
            @RequestParam("photo") MultipartFile photoFile) {

        EmployeeView createEmployeeView = EmployeeView.builder()
                .id(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .registrationNo(regNo)
                .birthDate(String.valueOf(birthDate))
                .photo(photoFile.getOriginalFilename().isEmpty() ? photoString : employeeMapper.multipartImageToString(photoFile))
                .build();
/*
        logger.info("Photo File : " + (photoFile.getOriginalFilename()));
 */
        employeeService.save(employeeMapper.toDomain(createEmployeeView));
        return "redirect:"+Url.EMPLOYEES_LIST;
    }
}