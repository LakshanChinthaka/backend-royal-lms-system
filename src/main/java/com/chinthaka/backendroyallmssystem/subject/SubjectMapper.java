package com.chinthaka.backendroyallmssystem.subject;


import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);

    SubjectDTO subjectToSubjectDTO(Subject subject);

    Subject subjectSaveDTOtoSubject(SubjectDTO subjectDTO);
}
