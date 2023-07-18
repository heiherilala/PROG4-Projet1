package com.hei.project2p1.controller.mapper.employeeType;

import com.hei.project2p1.modele.Phone;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class EmployeeView {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String photo;
    private String gender;
    private List<Phone> phones;
    private String address;
    private String personalEmail;
    private String professionalEmail;
    private String cinNumber;
    private String cinIssueDate;
    private String cinIssuePlace;
    private String function;
    private Integer numberOfChildren;
    private String hiringDate;
    private String departureDate;
    private String socioProfessionalCategory;
    private String cnapsNumber;
}
