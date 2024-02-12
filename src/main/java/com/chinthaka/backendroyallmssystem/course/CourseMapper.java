package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper courseMapper = Mappers.getMapper( CourseMapper.class );

    Course courseSaveDTOtoCourse(CourseDTO courseDTO);

    Course courseEditDTOtoCourse(CourseEditDTO courseEditDTO);
    CourseDTO courseToCourseSaveDTO(Course course);

    CourseResponseDTO courseToCourseResponseDTO(Course course);

//    Page<CourseResponseDTO> courseToCourseResponsePageDTO(Page<Course> course);

}
