package com.hei.project2p1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "spring_session")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpringSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_id")
    private String primaryId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "creation_time")
    private long creationTime;
    @Column(name = "last_access_time")
    private long lastAccessTime;
    @Column(name = "max_inactive_interval")
    private int maxInactiveInterval;
    @Column(name = "expiry_time")
    private long expiryTime;
    @Column(name = "principal_name")
    private String principalName;

}
