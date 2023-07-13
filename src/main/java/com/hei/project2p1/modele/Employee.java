package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Lob
    @Column//(columnDefinition = "varchar(max)")
    private String photo;
}
