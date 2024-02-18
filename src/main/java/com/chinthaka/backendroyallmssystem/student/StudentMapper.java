package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper studentMapper = Mappers.getMapper( StudentMapper.class );

    Student studentSaveDTOtoEntity(StudentDTO studentDTO);

    StudentDTO studentToStudentDto(Student student);

    StudentResponseDTO studentToStudentResponseDTO(Student student);

    //Pagination
    default Page<StudentResponseDTO> pageStudentToStudentResponseDTO(Page<Student> student) {
        List<StudentResponseDTO> studentResponseDTOList = student.getContent()
                .stream()
                .map(this::studentToStudentResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(studentResponseDTOList,
                student.getPageable(), student.getTotalElements());
    }
}
