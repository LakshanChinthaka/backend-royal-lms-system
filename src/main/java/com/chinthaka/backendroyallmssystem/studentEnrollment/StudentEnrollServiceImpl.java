package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.batch.BatchRepo;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.EnrollPaginationDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentEnrollServiceImpl implements IStudentEnrollService {

    private final StudentEnrollRepo studentEnrollRepo;
    private final BatchRepo batchRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    @Override
    public String studentEnroll(StudentEnrollDTO studentEnrollDTO) {
        log.info("Start Execute studentEnroll: {}", studentEnrollDTO);
        Batch batch = EntityUtils.getEntityDetails(
                studentEnrollDTO.getBatchId(), batchRepo, "Batch");
        Student student = EntityUtils.getEntityDetails(
                studentEnrollDTO.getStudentId(), studentRepo, "Student");
//        Course course = EntityUtils.getEntityDetails(
//                studentEnrollDTO.getStudentId(), courseRepo, "Course");
        if (studentEnrollRepo.existsByStudent(student)) {
            throw new AlreadyExistException(
                    "Student id: " + studentEnrollDTO.getStudentId() +
                            " Already Enrolled");
        }
        try {
            final StudentEnroll studentEnroll = new StudentEnroll(
//                    0L,
                    student,
                    batch,
                    batch.getCourse()
            );
            studentEnrollRepo.save(studentEnroll);
            return "Student id: " + studentEnrollDTO.getStudentId() + " Successfully Enrolled";
        } catch (Exception e) {
            log.error("Error while sStudent Enroll {}", e.getMessage());
            throw new HandleException("Something went wrong Student Enroll");
        }
    }

    @Override
    public String removeStudent(long studentId) {
        log.info("Start Execute removing student id: {}", studentId);
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        if (!studentEnrollRepo.existsByStudent(student)) {
            throw new NotFoundException("Student id: " + studentId + "Not found");
        }
        try {

        } catch (Exception e) {
            log.error("Error while removing student from batch {}", e.getMessage());
            throw new HandleException("Something went wrong removing student from batch");
        }
        return null;
    }

    @Override
    public Page<EnrollPaginationDTO> getAllStudentByBatch(long batchId, Pageable pageable) {
        log.info("Start execute getAllStudentByBatchId method {}", batchId);
        final Batch batch = EntityUtils.getEntityDetails(batchId, batchRepo, "Batch");
        try {
            Page<StudentEnroll> enrollPage = studentEnrollRepo.findAllByBatch(batch, pageable);
            return enrollPage.map(studentEnroll -> {
                EnrollPaginationDTO e = new EnrollPaginationDTO(
                        studentEnroll.getEnrollId(),
                        studentEnroll.getBatch().getBatchId(),
                        studentEnroll.getStudent().getId(),
                        studentEnroll.getStudent().getFirstName(),
                        studentEnroll.getStudent().getLastName(),
                        EntityUtils.convertToDateTime(studentEnroll.getCreatedDate()),
                        studentEnroll.getStudent().getNic(),
                        studentEnroll.getStudent().getGender(),
                        studentEnroll.getCreateBy()
                );
                return e;
            });

        } catch (Exception e) {
            log.error("Error while get pageable batch details: {}", e.getMessage());
            throw new HandleException("Something went wrong while get batch details");
        }
    }
}
