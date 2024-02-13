package com.edtech.elearning.repo;

import com.edtech.elearning.entity.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends JpaRepository<CoursesEntity, Integer>, JpaSpecificationExecutor<CoursesEntity> {

}