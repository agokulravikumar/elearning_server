package com.edtech.elearning.repo;

import com.edtech.elearning.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<UsersEntity, Integer>, JpaSpecificationExecutor<UsersEntity> {
    UsersEntity findByUsername(String userName);
}