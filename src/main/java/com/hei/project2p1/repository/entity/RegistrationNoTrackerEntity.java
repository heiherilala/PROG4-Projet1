package com.hei.project2p1.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "registration_no_tracker")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationNoTrackerEntity {
    @Id
    private int id;
    private Long lastNo;
}
