package com.hei.project2p1.repository.firm;

import com.hei.project2p1.repository.firm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity,String> {
    Optional<UsersEntity> findByUsername(String username);
}
