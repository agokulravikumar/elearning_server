package com.edtech.elearning.repo;

import com.edtech.elearning.entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentsRepo extends JpaRepository<StudentsEntity, Integer>, JpaSpecificationExecutor<StudentsEntity> {

}