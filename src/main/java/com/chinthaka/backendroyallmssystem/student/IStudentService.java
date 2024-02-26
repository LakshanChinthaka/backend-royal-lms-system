package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.request.StudentImageUploadDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface IStudentService {

    String addStudent(StudentDTO studentDTO);

//    String uploadImage(String imageUrl, long studentId);

    StudentResponseDTO studentFindById(long nic);

    String uploadStudentById(StudentDTO studentDTO, long studentId);

    String deleteStudent(long studentId);

    Page<StudentResponseDTO> getAllSubject(Pageable pageable);

    StudentResponseDTO studentFindByNic(String nic);

    Object findByStudentAndEmpByNic(String nic, String role);

    Object findEmail(String nic);

    String uploadImage(StudentImageUploadDTO imageUploadDTO);
}
