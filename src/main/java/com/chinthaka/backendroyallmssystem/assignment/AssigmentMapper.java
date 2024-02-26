package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.assignment.response.AssigmentResponseDTO;
import com.chinthaka.backendroyallmssystem.batch.BatchMapper;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AssigmentMapper {

    AssigmentMapper AssigmentMapper = Mappers.getMapper(AssigmentMapper.class );
//
//    //Pagination
//    default Page<AssigmentResponseDTO> pageStudentToStudentResponseDTO(Page<Assigment> assignment) {
//        List<StudentResponseDTO> studentResponseDTOList = assignment.getContent()
//                .stream()
//                .map(this::studentToStudentResponseDTO)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(studentResponseDTOList,
//                assignment.getPageable(), assignment.getTotalElements());
//    }
//
}
