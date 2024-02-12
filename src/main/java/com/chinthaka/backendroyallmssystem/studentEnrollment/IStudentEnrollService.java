package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;

public interface IStudentEnrollService {

    String studentEnroll(StudentEnrollDTO studentEnrollDTO);

    String removeStudent(long studentId, long batchId);
}
