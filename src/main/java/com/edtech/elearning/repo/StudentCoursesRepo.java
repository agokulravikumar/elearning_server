package com.edtech.elearning.repo;

import com.edtech.elearning.entity.StudentCoursesEntity;
import com.edtech.elearning.entity.StudentCoursesEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCoursesRepo extends JpaRepository<StudentCoursesEntity, StudentCoursesEntityPk>, JpaSpecificationExecutor<StudentCoursesEntity> {

}