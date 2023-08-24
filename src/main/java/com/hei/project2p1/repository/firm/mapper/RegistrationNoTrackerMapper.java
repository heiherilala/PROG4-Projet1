package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.RegistrationNoTracker;
import com.hei.project2p1.repository.firm.entity.RegistrationNoTrackerEntity;
import org.springframework.stereotype.Component;

@Component
public class RegistrationNoTrackerMapper {
    public RegistrationNoTracker toDomain(RegistrationNoTrackerEntity entity) {
        return RegistrationNoTracker.builder()
                .id(entity.getId())
                .lastNo(entity.getLastNo())
                .build();
    }

    public RegistrationNoTrackerEntity toEntity(RegistrationNoTracker domain) {
        return RegistrationNoTrackerEntity.builder()
                .id(domain.getId())
                .lastNo(domain.getLastNo())
                .build();
    }
}

