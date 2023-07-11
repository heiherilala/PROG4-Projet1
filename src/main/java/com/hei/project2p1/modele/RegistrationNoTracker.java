package com.hei.project2p1.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationNoTracker {
    @Id
    private int id;
    private Long lastNo;
}
