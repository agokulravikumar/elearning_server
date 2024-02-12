package com.edtech.elearning.repo;

import com.edtech.elearning.entity.CourseModulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseModulesRepo extends JpaRepository<CourseModulesEntity, Integer>, JpaSpecificationExecutor<CourseModulesEntity> {

}