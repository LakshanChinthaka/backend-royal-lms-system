package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitGradeDTO;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitRequestDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssigmentResponseDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssignmentResposeDTOforStudent;
import com.chinthaka.backendroyallmssystem.assignment.response.SubmitResponseForAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAssigmentService {

    String addAssigment(AssigmentAddDTO assigmentAddDTO);

    Page<AssigmentResponseDTO> getAllAssigment(Pageable pageable);

    String deleteAssignment(long assId);

    Page<AssignmentResposeDTOforStudent> getAllAssigmentByStudent(Pageable pageable, long studentId);

    String submitAssigment(SubmitRequestDTO submitRequestDTO);

    Page<SubmitResponseForAdmin> getAllAssigmentForAdmin(Pageable pageable);

    String addGrade(SubmitGradeDTO submitGradeDTO);
}
