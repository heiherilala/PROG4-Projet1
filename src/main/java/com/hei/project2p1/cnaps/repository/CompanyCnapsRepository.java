package com.hei.project2p1.cnaps.repository;

import com.hei.project2p1.cnaps.entity.CompanyCnapsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCnapsRepository extends JpaRepository<CompanyCnapsEntity,String> {

}
