package com.hei.project2p1.controller.Mapper.EmployeeType;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EmployeeUI {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String photo;
}
