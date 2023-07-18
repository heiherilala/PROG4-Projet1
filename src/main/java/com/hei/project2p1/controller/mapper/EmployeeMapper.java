package com.hei.project2p1.controller.mapper;

import com.hei.project2p1.controller.mapper.employeeType.CreateEmployeeView;
import com.hei.project2p1.controller.mapper.employeeType.EmployeeView;
import com.hei.project2p1.modele.Employee;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {
    public Employee toDomain(CreateEmployeeView createEmployeeView) {
        return Employee.builder()
                .id(createEmployeeView.getId()==null?null:Integer.parseInt(createEmployeeView.getId()))
                .lastName(createEmployeeView.getLastName())
                .firstName(createEmployeeView.getFirstName())
                .registrationNo(createEmployeeView.getRegistrationNo())
                .birthDate(LocalDate.parse(createEmployeeView.getBirthDate()))
                .photo(multipartImageToString(createEmployeeView.getPhoto()))
                .gender(Employee.Gender.valueOf(createEmployeeView.getGender()))
                .address(createEmployeeView.getAddress())
                .cinIssueDate(LocalDate.parse(createEmployeeView.getCinIssueDate()))
                .cinIssuePlace(createEmployeeView.getCinIssuePlace())
                .cinNumber(createEmployeeView.getCinNumber())
                .cnapsNumber(createEmployeeView.getCnapsNumber())
                .function(createEmployeeView.getFunction())
                .numberOfChildren(createEmployeeView.getNumberOfChildren())
                .socioProfessionalCategory(Employee.SocioProfessionalCategory.valueOf(createEmployeeView.getSocioProfessionalCategory()))
                .hiringDate(LocalDate.parse(createEmployeeView.getHiringDate()))
                .departureDate(LocalDate.parse(createEmployeeView.getDepartureDate()))
                .phones(createEmployeeView.getPhones())
                .personalEmail(createEmployeeView.getPersonalEmail())
                .professionalEmail(createEmployeeView.getProfessionalEmail())
                .build();
    }

    public Employee toDomain(EmployeeView createEmployeeView) {
        return Employee.builder()
                .id(createEmployeeView.getId()==null?null:Integer.parseInt(createEmployeeView.getId()))
                .lastName(createEmployeeView.getLastName())
                .firstName(createEmployeeView.getFirstName())
                .registrationNo(createEmployeeView.getRegistrationNo())
                .birthDate(LocalDate.parse(createEmployeeView.getBirthDate()))
                .photo(createEmployeeView.getPhoto())
                .gender(Employee.Gender.valueOf(createEmployeeView.getGender()))
                .address(createEmployeeView.getAddress())
                .cinIssueDate(LocalDate.parse(createEmployeeView.getCinIssueDate()))
                .cinIssuePlace(createEmployeeView.getCinIssuePlace())
                .cinNumber(createEmployeeView.getCinNumber())
                .cnapsNumber(createEmployeeView.getCnapsNumber())
                .function(createEmployeeView.getFunction())
                .numberOfChildren(createEmployeeView.getNumberOfChildren())
                .socioProfessionalCategory(Employee.SocioProfessionalCategory.valueOf(createEmployeeView.getSocioProfessionalCategory()))
                .hiringDate(LocalDate.parse(createEmployeeView.getHiringDate()))
                .departureDate(LocalDate.parse(createEmployeeView.getDepartureDate()))
                .phones(createEmployeeView.getPhones())
                .personalEmail(createEmployeeView.getPersonalEmail())
                .professionalEmail(createEmployeeView.getProfessionalEmail())
                .build();
    }


    public List<Employee> toDomain(List<CreateEmployeeView> createEmployeeViews) {
        List<Employee> employees = new ArrayList<>();
        for (CreateEmployeeView createEmployeeView : createEmployeeViews){
            employees.add(toDomain(createEmployeeView));
        }
        return employees;
    }

    public EmployeeView toUI(Employee employee){

        return EmployeeView.builder()
                .id(String.valueOf(employee.getId()))
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(String.valueOf(employee.getBirthDate()))
                .registrationNo(employee.getRegistrationNo())
                .photo(employee.getPhoto())
                .build();
    }

    public List<EmployeeView> toUI(List<Employee> employees){
        List<EmployeeView> createEmployeeViews = new ArrayList<>();
        for (Employee employee: employees){
            createEmployeeViews.add(toUI(employee));
        }
        return createEmployeeViews;
    }

    public String multipartImageToString(MultipartFile multipartFile) {
        String result;
        try {
            byte[] image = Base64.encodeBase64(multipartFile.getBytes(),true);
            result = new String(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

