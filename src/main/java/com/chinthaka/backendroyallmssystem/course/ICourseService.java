package com.chinthaka.backendroyallmssystem.course;


import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {
    String createCourse(CourseDTO courseDTO);

    CourseResponseDTO courseGetById(long courseId);

    String uploadCourse(CourseEditDTO courseEditDTO, long courseId);

    String deleteStudent(long courseId);

    Page<CourseResponseDTO> getAllCourse(Pageable pageable);
}
