package com.hei.project2p1.repository.firm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "registration_no_tracker")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationNoTrackerEntity {
    @Id
    private int id;
    private Long lastNo;
}
