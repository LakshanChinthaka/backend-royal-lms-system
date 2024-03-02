package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.EnrollPaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentEnrollService {

    String studentEnroll(StudentEnrollDTO studentEnrollDTO);

    String removeStudent(long enrollId);


    Page<EnrollPaginationDTO> getAllStudentByBatch(long batchId,Pageable pageable);
}
