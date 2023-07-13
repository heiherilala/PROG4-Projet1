package com.hei.project2p1.controller.Mapper;

import com.hei.project2p1.controller.Mapper.EmployeeType.CreateEmployeeUI;
import com.hei.project2p1.controller.Mapper.EmployeeType.EmployeeUI;
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
    public Employee toDomain(CreateEmployeeUI createEmployeeUI) throws IOException {
        return Employee.builder()
                .id(createEmployeeUI.getId()==null?null:Integer.parseInt(createEmployeeUI.getId()))
                .lastName(createEmployeeUI.getLastName())
                .firstName(createEmployeeUI.getFirstName())
                .registrationNo(createEmployeeUI.getRegistrationNo())
                .birthDate(LocalDate.parse(createEmployeeUI.getBirthDate()))
                .photo(MultipartImageToString(createEmployeeUI.getPhoto()))
                .build();
    }

    public List<Employee> toDomain(List<CreateEmployeeUI> createEmployeeUIS) throws IOException {
        List<Employee> employees = new ArrayList<>();
        for (CreateEmployeeUI createEmployeeUI : createEmployeeUIS){
            employees.add(toDomain(createEmployeeUI));
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
        List<EmployeeUI> createEmployeeUIS = new ArrayList<>();
        for (Employee employee: employees){
            createEmployeeUIS.add(toUI(employee));
        }
        return createEmployeeUIS;
    }

    private String MultipartImageToString(MultipartFile multipartFile) throws IOException {
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

