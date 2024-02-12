package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.batch.BatchRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentEnrollServiceImpl implements IStudentEnrollService {

    private final StudentEnrollRepo studentEnrollRepo;
    private final BatchRepo batchRepo;
    private final StudentRepo studentRepo;

    @Override
    public String studentEnroll(StudentEnrollDTO studentEnrollDTO) {
        Batch batch = EntityUtils.getEntityDetails(
                studentEnrollDTO.getBatchId(), batchRepo, "Batch");
        Student student = EntityUtils.getEntityDetails(
                studentEnrollDTO.getStudentId(), studentRepo, "Student");
        if (studentEnrollRepo.existsByStudentAndBatch(student,batch)){
            throw new AlreadyExistException(
                    "Student id: "+studentEnrollDTO.getStudentId()+ " Already Enrolled");
        }
        try {
            final StudentEnroll studentEnroll = new StudentEnroll(
                    0L,
                    student,
                    batch
            );
            studentEnrollRepo.save(studentEnroll);
            return "Student id: " + studentEnrollDTO.getStudentId() + " Successfully Enrolled";
        } catch (Exception e) {
            log.error("Error while sStudent Enroll {}", e.getMessage());
            throw new HandleException("Something went wrong Student Enroll");
        }
    }

    @Override
    public String removeStudent(long studentId, long batchId) {
        return null;
    }
}
