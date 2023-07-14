package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "employee")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_registration_No",
        columnNames = { "registrationNo" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //or @Column(unique=true) use on creation of db
    private String registrationNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Lob
    @Column //(columnDefinition = "varchar(max)")?
    private String photo;
}
