package com.hei.project2p1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Employee implements Serializable {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private List<Phone> phones;
    private String address;
    private String personalEmail;
    private String professionalEmail;
    private String cinNumber;
    private LocalDate cinIssueDate;
    private String cinIssuePlace;
    private String function;
    private Integer numberOfChildren;
    private LocalDate hiringDate;
    private LocalDate departureDate;
    private SocioProfessionalCategory socioProfessionalCategory;
    private String cnapsNumber;
    private String photo;
    private String endToEndId;
    private Integer monthlySalary;

    public enum Gender {
        H, F
    }
    public enum SocioProfessionalCategory {
        M1, M2, OS1, OS2, OS3, OP1A, OP1B, OP2, OP3
    }
}
