package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.SpringSession;
import com.hei.project2p1.repository.firm.entity.SpringSessionEntity;
import org.springframework.stereotype.Component;

@Component
public class SpringSessionMapper {
    public SpringSession toDomain(SpringSessionEntity entity) {
        return SpringSession.builder()
                .primaryId(entity.getPrimaryId())
                .sessionId(entity.getSessionId())
                .creationTime(entity.getCreationTime())
                .lastAccessTime(entity.getLastAccessTime())
                .maxInactiveInterval(entity.getMaxInactiveInterval())
                .expiryTime(entity.getExpiryTime())
                .principalName(entity.getPrincipalName())
                .build();
    }

    public SpringSessionEntity toEntity(SpringSession domain) {
        return SpringSessionEntity.builder()
                .primaryId(domain.getPrimaryId())
                .sessionId(domain.getSessionId())
                .creationTime(domain.getCreationTime())
                .lastAccessTime(domain.getLastAccessTime())
                .maxInactiveInterval(domain.getMaxInactiveInterval())
                .expiryTime(domain.getExpiryTime())
                .principalName(domain.getPrincipalName())
                .build();
    }
}

