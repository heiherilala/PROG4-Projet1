package com.hei.project2p1.repository.firm.mapper;

import com.hei.project2p1.model.Users;
import com.hei.project2p1.repository.firm.entity.UsersEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    public Users toDomain(UsersEntity entity) {
        return Users.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .enabled(entity.isEnabled())
                .build();
    }

    public UsersEntity toEntity(Users entity) {
        return UsersEntity.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .enabled(entity.isEnabled())
                .build();
    }
}
