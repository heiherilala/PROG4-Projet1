package com.hei.project2p1.repository;

import com.hei.project2p1.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {
    Optional<Users> findByUsername(String username);
}
