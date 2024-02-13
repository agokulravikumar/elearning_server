package com.edtech.elearning.repo;

import com.edtech.elearning.entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepo extends JpaRepository<StudentsEntity, Integer>, JpaSpecificationExecutor<StudentsEntity> {

}