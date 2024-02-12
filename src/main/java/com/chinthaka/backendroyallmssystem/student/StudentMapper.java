package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper studentMapper = Mappers.getMapper( StudentMapper.class );

    Student studentSaveDTOtoEntity(StudentDTO studentDTO);

    StudentDTO studentToStudentDto(Student student);

    StudentResponseDTO studentToStudentResponseDTO(Student student);

}
