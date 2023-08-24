package com.hei.project2p1.repository.cnaps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number"},name = "unique_phone_number_constraint"),
})
public class PhoneCnapsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String number;
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private EmployeeCnapsEntity employee;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyCnapsEntity company;
}
