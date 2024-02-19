package com.chinthaka.backendroyallmssystem.studentEnrollment;


import com.chinthaka.backendroyallmssystem.studentEnrollment.response.StudentEnrollResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentEnrollMapper {

    StudentEnrollMapper enrollMapper = Mappers.getMapper(StudentEnrollMapper.class);

    StudentEnrollResponseDTO enrollEntityToResponse(StudentEnroll studentEnroll);
}
