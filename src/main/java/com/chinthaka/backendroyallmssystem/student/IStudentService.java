package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;

public interface IStudentService {

    String addStudent(StudentDTO studentDTO);

    String uploadImage(String imageUrl, long studentId);

    StudentResponseDTO studentFindById(long studentId);

    String uploadStudentById(StudentDTO studentDTO, long studentId);

    String deleteStudent(long studentId);

}
