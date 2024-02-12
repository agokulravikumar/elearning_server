package com.edtech.elearning.repo;

import com.edtech.elearning.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsersRepo extends JpaRepository<UsersEntity, Integer>, JpaSpecificationExecutor<UsersEntity> {

}