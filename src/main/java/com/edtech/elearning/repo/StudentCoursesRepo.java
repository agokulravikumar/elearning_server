package com.edtech.elearning.repo;

import com.edtech.elearning.entity.StudentCoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentCoursesRepo extends JpaRepository<StudentCoursesEntity, Integer>, JpaSpecificationExecutor<StudentCoursesEntity> {

}