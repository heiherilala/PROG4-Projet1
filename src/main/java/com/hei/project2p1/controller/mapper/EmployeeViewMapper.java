package com.hei.project2p1.controller.mapper;

import com.hei.project2p1.controller.mapper.modelView.EmployeeView;
import com.hei.project2p1.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.stringInputOfString;
import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.stringInputValueToLocalDate;
import static com.hei.project2p1.controller.mapper.utils.ConvertNullValueToView.valueToView;

@Component
public class EmployeeViewMapper {

    public Employee toDomain(EmployeeView createEmployeeView) {

        return Employee.builder()
                .id(createEmployeeView.getId()!=null?
                        createEmployeeView.getId()
                        :null)
                .lastName(stringInputOfString(createEmployeeView.getLastName()))
                .firstName(createEmployeeView.getFirstName())
                .registrationNo(createEmployeeView.getRegistrationNo())
                .birthDate(stringInputValueToLocalDate(createEmployeeView.getBirthDate()))
                .photo(createEmployeeView.getPhoto())
                .gender(!createEmployeeView.getGender().isEmpty() ?
                        Employee.Gender.valueOf(createEmployeeView.getGender())
                        :null)
                .address(stringInputOfString(createEmployeeView.getAddress()))
                .cinIssueDate(stringInputValueToLocalDate(createEmployeeView.getCinIssueDate()))
                .cinIssuePlace(stringInputOfString(createEmployeeView.getCinIssuePlace()))
                .cinNumber(stringInputOfString(createEmployeeView.getCinNumber()))
                .cnapsNumber(stringInputOfString(createEmployeeView.getCnapsNumber()))
                .function(stringInputOfString(createEmployeeView.getFunction()))
                .numberOfChildren(createEmployeeView.getNumberOfChildren())
                .socioProfessionalCategory(!createEmployeeView.getSocioProfessionalCategory().isEmpty() ?
                        Employee.SocioProfessionalCategory.valueOf(createEmployeeView.getSocioProfessionalCategory())
                        :null)
                .hiringDate(stringInputValueToLocalDate(createEmployeeView.getHiringDate()))
                .departureDate(stringInputValueToLocalDate(createEmployeeView.getDepartureDate()))
                .phones(List.of())
                .personalEmail(stringInputOfString(createEmployeeView.getPersonalEmail()))
                .professionalEmail(stringInputOfString(createEmployeeView.getProfessionalEmail()))
                .build();
    }


    public EmployeeView toView(Employee employee){

        return EmployeeView.builder()
                .id(String.valueOf(employee.getId()))
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(valueToView(employee.getBirthDate()))
                .registrationNo(employee.getRegistrationNo())
                .photo(employee.getPhoto())
                .gender(String.valueOf(employee.getGender()))
                .address(employee.getAddress())
                .cinIssueDate(valueToView(employee.getCinIssueDate()))
                .cinIssuePlace(employee.getCinIssuePlace())
                .cinNumber(employee.getCinNumber())
                .cnapsNumber(employee.getCnapsNumber())
                .function(employee.getFunction())
                .numberOfChildren(employee.getNumberOfChildren())
                .socioProfessionalCategory(String.valueOf(employee.getSocioProfessionalCategory()))
                .hiringDate(valueToView(employee.getHiringDate()))
                .departureDate(valueToView(employee.getDepartureDate()))
                .phones(employee.getPhones()==null?List.of()
                        :employee.getPhones().stream().map(phone -> phone.getCountryCode()+","+phone.getNumber()).toList())
                .personalEmail(employee.getPersonalEmail())
                .professionalEmail(employee.getProfessionalEmail())
                .build();
    }

    public List<EmployeeView> toView(List<Employee> employees){
        List<EmployeeView> employeeViews = new ArrayList<>();
        for (Employee employee: employees){
            employeeViews.add(toView(employee));
        }
        return employeeViews;
    }

}

