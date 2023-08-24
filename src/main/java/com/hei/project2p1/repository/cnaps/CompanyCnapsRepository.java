package com.hei.project2p1.repository.cnaps;

import com.hei.project2p1.repository.cnaps.entity.CompanyCnapsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCnapsRepository extends JpaRepository<CompanyCnapsEntity,String> {

}
