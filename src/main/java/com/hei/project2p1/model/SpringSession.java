package com.hei.project2p1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class SpringSession implements Serializable {
    private String primaryId;
    private String sessionId;
    private long creationTime;
    private long lastAccessTime;
    private int maxInactiveInterval;
    private long expiryTime;
    private String principalName;

}
