package com.chinthaka.backendroyallmssystem.course;


import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseForDrop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
    String createCourse(CourseDTO courseDTO,String imageUrl);

    CourseResponseDTO courseGetById(long courseId);

    String uploadCourse(CourseEditDTO courseEditDTO, long courseId);

    String deleteStudent(long courseId);

    Page<CourseResponseDTO> getAllCourse(Pageable pageable);

    List<CourseResponseForDrop> getAll();
}
