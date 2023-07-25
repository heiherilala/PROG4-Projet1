package com.hei.project2p1.controller;

import com.hei.project2p1.controller.constant.EmployeeUrl;
import com.hei.project2p1.controller.mapper.EmployeeMapper;
import com.hei.project2p1.controller.mapper.modelView.EmployeeView;
import com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @GetMapping(value = EmployeeUrl.EMPLOYEES_LIST)
    public String index(@RequestParam(value = "page", defaultValue = "1") int pageNo,
                        @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort_by", defaultValue = "lastName") String sortBy,
                        @RequestParam(value = "sort_order", defaultValue = "ASC") String sortOrder,
                        @RequestParam(value = "last_name",required = false, defaultValue = "") String lastName,
                        @RequestParam(value = "first_name",required = false, defaultValue = "") String firstName,
                        @RequestParam(value = "function",required = false, defaultValue = "") String function,
                        @RequestParam(value = "gender",required = false, defaultValue = "") String gender,
                        @RequestParam(value = "entrance_before",required = false) LocalDate entranceDateBefore,
                        @RequestParam(value = "entrance_after",required = false) LocalDate entranceDateAfter,
                        @RequestParam(value = "leave_before",required = false) LocalDate leaveDateBefore,
                        @RequestParam(value = "leave_after",required = false) LocalDate leaveDateAfter,
                        Model model) {

        List<Employee> employees = employeeService.findEmployeesByCriteria(
                firstName,
                lastName,
                function,
                gender,
                entranceDateAfter, entranceDateBefore,
                leaveDateAfter, leaveDateBefore,
                pageNo, pageSize, sortBy, sortOrder);
        long totalPages = employeeService.getTotalPages(pageSize);
        List<EmployeeView> employeesView = employeeMapper.toView(employees);
        List<String> genderList = Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        //variable to Display
        model.addAttribute("employees", employeesView);
        model.addAttribute("genderList", genderList);
        //Sort th variables
        model.addAttribute("sortField", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        //Pagination th variables
        model.addAttribute("page", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
        //Search th variables
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("gender", gender);
        model.addAttribute("function", function);
        model.addAttribute("entrance_before", entranceDateBefore);
        model.addAttribute("entrance_after", entranceDateAfter);
        model.addAttribute("leave_before", leaveDateBefore);
        model.addAttribute("leave_after", leaveDateAfter);

        return "index";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_ADD)
    public String addNewEmployee( Model model) {
        List<String> genderList= Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("genders", genderList);
        List<String> categories = Stream.of(Employee.SocioProfessionalCategory.values()).map(Enum::name).toList();
        model.addAttribute("categories", categories);
        model.addAttribute("phones", new ArrayList<>());
        return "add-employee";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_UPDATE)
    public String modifyEmployeePage( Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employeeMapper.toView(employee));

        List<String> categories = Stream.of(Employee.SocioProfessionalCategory.values()).map(Enum::name).toList();
        model.addAttribute("categories", categories);
        List<String> genderList= Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("genders", genderList);
        return "update-employee";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_DETAILS)
    public String details(Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeView createEmployeeView = employeeMapper.toView(employee);
        model.addAttribute("employee", createEmployeeView);
        return "employee_details";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("gender") String gender,
            @RequestParam("phones") List<String> phones,
            @RequestParam("address") String address,
            @RequestParam("personalEmail") String personalEmail,
            @RequestParam("professionalEmail") String professionalEmail,
            @RequestParam("cinNumber") String cinNumber,
            @RequestParam("cinIssueDate") String cinIssueDate,
            @RequestParam("cinIssuePlace") String cinIssuePlace,
            @RequestParam("function") String function,
            @RequestParam("numberOfChildren") Integer numberOfChildren,
            @RequestParam("hiringDate") String hiringDate,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("socioProfessionalCategory") String socioProfessionalCategory,
            @RequestParam("cnapsNumber") String cnapsNumber,
            Model model
    ) {
        String photoTreated = ConvertInputTypeToDomain.multipartImageToString(photo);

        EmployeeView employee = EmployeeView.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .photo(photoTreated)
                .gender(gender)
                .phones(phones==null?new ArrayList<>():phones)
                .address(address)
                .personalEmail(personalEmail)
                .professionalEmail(professionalEmail)
                .cinNumber(cinNumber)
                .cinIssueDate(cinIssueDate)
                .cinIssuePlace(cinIssuePlace)
                .function(function)
                .numberOfChildren(numberOfChildren)
                .hiringDate(hiringDate)
                .departureDate(departureDate)
                .socioProfessionalCategory(socioProfessionalCategory)
                .cnapsNumber(cnapsNumber)
                .registrationNo(null)
                .build();
        //logger.info(employee.toString());
        employeeService.save(employeeMapper.toDomain(employee), employee.getPhones());
        return "redirect:"+ EmployeeUrl.EMPLOYEES_LIST;
    }

    @PostMapping("/modifyEmployee")
    public String modifyEmployee(

            @RequestParam("photoString") String photoString,
            @RequestParam("id") String id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("gender") String gender,
            @RequestParam("phones") List<String> phones,
            @RequestParam("address") String address,
            @RequestParam("personalEmail") String personalEmail,
            @RequestParam("professionalEmail") String professionalEmail,
            @RequestParam("cinNumber") String cinNumber,
            @RequestParam("cinIssueDate") String cinIssueDate,
            @RequestParam("cinIssuePlace") String cinIssuePlace,
            @RequestParam("function") String function,
            @RequestParam("numberOfChildren") Integer numberOfChildren,
            @RequestParam("hiringDate") String hiringDate,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("socioProfessionalCategory") String socioProfessionalCategory,
            @RequestParam("cnapsNumber") String cnapsNumber
            ) {

        EmployeeView employee = EmployeeView.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .photo(photo.getOriginalFilename().isEmpty()? photoString : ConvertInputTypeToDomain.multipartImageToString(photo) )
                .gender(gender)
                .phones(phones==null?new ArrayList<>():phones)
                .address(address)
                .personalEmail(personalEmail)
                .professionalEmail(professionalEmail)
                .cinNumber(cinNumber)
                .cinIssueDate(cinIssueDate)
                .cinIssuePlace(cinIssuePlace)
                .function(function)
                .numberOfChildren(numberOfChildren)
                .hiringDate(hiringDate)
                .departureDate(departureDate)
                .socioProfessionalCategory(socioProfessionalCategory)
                .cnapsNumber(cnapsNumber)
                .registrationNo(null)
                .build();

        //logger.info("Photo File : " + (photoFile.getOriginalFilename()));
        employeeService.save(employeeMapper.toDomain(employee), employee.getPhones());
        return "redirect:"+"/employees/"+id+"/details";
    }
}