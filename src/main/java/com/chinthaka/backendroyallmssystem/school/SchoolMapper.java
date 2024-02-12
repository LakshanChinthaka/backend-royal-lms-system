package com.chinthaka.backendroyallmssystem.school;


import com.chinthaka.backendroyallmssystem.school.request.SchoolDTO;
import com.chinthaka.backendroyallmssystem.school.response.SchoolResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    SchoolMapper schoolMapper = Mappers.getMapper(SchoolMapper.class );

    School schoolDTOtoSchool(SchoolDTO schoolDTO);

    SchoolDTO schooToSchoolDTO(School school);

    @Mapping(target = "activeStatus", expression = "java(school.isActiveStatus() ? \"Active\" : \"Inactive\")")
    List<SchoolResponseDTO> listSchoolToListSchoolResponseDto(List<School> schools);


}
