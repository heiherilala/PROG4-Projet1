package com.hei.project2p1.repository.firm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "spring_session")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SpringSessionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String primaryId;
    private String sessionId;
    private long creationTime;
    private long lastAccessTime;
    private int maxInactiveInterval;
    private long expiryTime;
    private String principalName;

}
