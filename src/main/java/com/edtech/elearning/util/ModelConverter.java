package com.edtech.elearning.util;

import com.edtech.elearning.entity.StudentCoursesEntity;
import com.edtech.elearning.model.StudentCourse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelConverter {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        initModelMapper();
    }

    public <T> T map(Object entity, Class<T> model) {
        return modelMapper.map(entity, model);
    }

    private void initModelMapper() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.typeMap(StudentCoursesEntity.class, StudentCourse.class)
                .addMappings(m -> m.map(src -> src.getPk().getStudentId(), StudentCourse::setStudentId))
                .addMappings(m -> m.map(src -> src.getPk().getCourseId(), StudentCourse::setCourseId));
    }

}
