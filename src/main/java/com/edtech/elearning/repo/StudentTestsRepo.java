package com.edtech.elearning.repo;

import com.edtech.elearning.entity.StudentTestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentTestsRepo extends JpaRepository<StudentTestsEntity, Integer>, JpaSpecificationExecutor<StudentTestsEntity> {

}