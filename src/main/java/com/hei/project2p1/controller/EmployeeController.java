package com.hei.project2p1.controller;

import com.hei.project2p1.controller.constant.EmployeeUrl;
import com.hei.project2p1.controller.mapper.EmployeeViewMapper;
import com.hei.project2p1.controller.mapper.modelView.EmployeeView;
import com.hei.project2p1.exception.BadRequestException;
import com.hei.project2p1.model.Company;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.CompanyService;
import com.hei.project2p1.service.EmployeeService;
import com.hei.project2p1.service.SpringSessionService;
import com.hei.project2p1.utils.ObjectToCSVConverter;
import com.hei.project2p1.utils.PhoneFormatting;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.multipartImageToString;
import static com.hei.project2p1.controller.mapper.utils.LoadFiles.getImageAsBase64;
import static com.hei.project2p1.controller.utils.CustomResponse.convertHtmlToPdf;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeViewMapper employeeViewMapper;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final SpringSessionService springSessionService;
    private final SpringTemplateEngine springTemplateEngine;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @GetMapping(value = "/employees/{id}/pdf")
    public void getEmployeeSheetPdf(
            @PathVariable("id") String id,
            @RequestParam("option") String option,
            HttpServletResponse response
    ) {

        Employee employee = employeeService.getEmployeeById(id);
        EmployeeView employeeView = employeeViewMapper.toView(employee, option);
        Company company = companyService.getCompanyInfo();
        String logo = getImageAsBase64("static/image/logo.png");

        Context context= new Context();
        context.setVariable("employee", employeeView);
        context.setVariable("company", company);
        context.setVariable("logo", logo);
        employeeView.setPhoto(employeeView.getPhoto().replace(" ",""));
        context.setVariable("photo", Base64.getEncoder().encodeToString(employeeView.getPhoto().getBytes()));

        String dateRef ="-"+ LocalDate.now().getMonth()+"-"+ LocalDate.now().getYear();
        String html = springTemplateEngine.process("employee-pdf-templete", context);
        convertHtmlToPdf(employeeView.getRegistrationNo()+dateRef,html, response);
    }


    @GetMapping(value = "/")
    public String redirection(){
        return "redirect:"+ EmployeeUrl.EMPLOYEES_LIST;
    }


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
                        @RequestParam(value = "country_code",required = false) String countryCode,
                        Model model,
                        HttpSession session

    ) {
        List<Employee> employees = employeeService.findEmployeesByCriteria(
                firstName, lastName, function, countryCode, gender, entranceDateAfter, entranceDateBefore,
                leaveDateAfter, leaveDateBefore, pageNo, pageSize, sortBy, sortOrder);
        long totalPages = employeeService.getTotalPages(pageSize);
        List<EmployeeView> employeesView = employeeViewMapper.toView(employees, null);
        List<String> genderList = Stream.of(Employee.Gender.values()).map(Enum::name).toList();

        for (EmployeeView ev:employeesView) {
            ev.setPhones(ev.getPhones().stream().map(phone -> PhoneFormatting.reformatPhoneNumber(phone).replace(" ","")).toList());
        }
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
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("gender", gender);
        model.addAttribute("function", function);
        model.addAttribute("entrance_before", entranceDateBefore);
        model.addAttribute("entrance_after", entranceDateAfter);
        model.addAttribute("leave_before", leaveDateBefore);
        model.addAttribute("leave_after", leaveDateAfter);

        model.addAttribute("session_id", session.getId());
        model.addAttribute("session", springSessionService.getBySessionId(session.getId()));

        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);

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

        model.addAttribute("employee", employeeViewMapper.toView(employee, null));

        List<String> categories = Stream.of(Employee.SocioProfessionalCategory.values()).map(Enum::name).toList();
        model.addAttribute("categories", categories);
        List<String> genderList= Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("genders", genderList);
        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);
        return "update-employee";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_DETAILS)
    public String details(Model model, @PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeView employeeView = employeeViewMapper.toView(employee, null);
        employeeView.setPhones(employeeView.getPhones().stream().map(PhoneFormatting::reformatPhoneNumber).toList());
        model.addAttribute("employee", employeeView);
        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);
        List<String> optionList= Stream.of(EmployeeViewMapper.AgeOption.values()).map(Enum::name).toList();
        model.addAttribute("options", optionList);
        return "details-employee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("gender") String gender,
            @RequestParam("phones") List<String> phones,
            @RequestParam("countryCodes") List<String> countryCodes,
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
            @RequestParam(value = "monthlySalary", required = false) Integer monthlySalary,
            Model model
    ) {
        String photoTreated = multipartImageToString(photo);
        if (countryCodes!=null && phones!=null && countryCodes.size()!=phones.size()){
            throw new BadRequestException("country code and phone number must be specified at the same time");
        }
        EmployeeView employee = EmployeeView.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .photo(photoTreated)
                .gender(gender)
                .phones(phones==null?List.of():phones)
                .codeCountry(countryCodes==null?List.of():countryCodes)
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
                .monthlySalary(monthlySalary)
                .cnapsNumber(null)
                .registrationNo(null)
                .build();
        employeeService.save(employeeViewMapper.toDomain(employee), employee.getCodeCountry() , employee.getPhones());
        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);
        return "redirect:"+EmployeeUrl.EMPLOYEES_LIST;
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
            @RequestParam(value = "phones", required = false) List<String> phones,
            @RequestParam(value = "countryCodes", required = false) List<String> countryCodes,
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
            @RequestParam(value = "monthlySalary", required = false) Integer monthlySalary,
            Model model
            ) {
        if (countryCodes!=null && phones!=null && countryCodes.size()!=phones.size()){
            throw new BadRequestException("country code and phone number must be specified at the same time");
        }
        EmployeeView employee = EmployeeView.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .photo(photo.getOriginalFilename().isEmpty()? photoString : multipartImageToString(photo) )
                .gender(gender)
                .phones(phones==null?List.of():phones)
                .codeCountry(countryCodes==null?List.of():countryCodes)
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
                .monthlySalary(monthlySalary)
                .cnapsNumber(null)
                .registrationNo(null)
                .build();

        employeeService.save(employeeViewMapper.toDomain(employee), employee.getCodeCountry(), employee.getPhones());
        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);
        return "redirect:"+"/employees/"+id+"/details";
    }

    @GetMapping("/exportEmployees")
    public ResponseEntity<ByteArrayResource> exportEmployeesCsv(@RequestParam(value = "page", defaultValue = "1") int pageNo,
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

                                                                @RequestParam(value = "country_code",required = false) String countryCode,
                                                                @RequestParam(value = "leave_after",required = false) LocalDate leaveDateAfter,
                                                                Model model){

        List<Employee> employees = employeeService.findEmployeesByCriteria(
                firstName,
                lastName,
                function,
                countryCode,
                gender,
                entranceDateAfter, entranceDateBefore,
                leaveDateAfter, leaveDateBefore,
                pageNo, pageSize, sortBy, sortOrder);
        List<EmployeeView> employeesView = employeeViewMapper.toView(employees, null);
        for (EmployeeView ev:employeesView) {
            ev.setPhones(ev.getPhones().stream().map(PhoneFormatting::reformatPhoneNumber).toList());
        }
        String converted = ObjectToCSVConverter.convertToCSV(employeesView,List.of("photo"),List.of("phones","codeCountry"));
        byte[] bytes = converted.getBytes();
        ByteArrayResource resource = new ByteArrayResource(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.csv");
        Company company = companyService.getCompanyInfo();
        model.addAttribute("company", company);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

}