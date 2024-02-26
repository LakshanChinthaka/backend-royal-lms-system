package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.AssigmentSubmit;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.AssignmentSubmitRepo;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitGradeDTO;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitRequestDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssigmentResponseDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssignmentResposeDTOforStudent;
import com.chinthaka.backendroyallmssystem.assignment.response.SubmitResponseForAdmin;
import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.batch.BatchRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.student.IStudentService;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.student.StudentMapper;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnroll;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AssigmentServiceImpl implements IAssigmentService {

    private final AssigmentRepo assigmentRepo;
    private final BatchRepo batchRepo;
    private final StudentEnrollRepo enrollRepo;
    private final IStudentService studentService;
    private final StudentMapper studentMapper;
    private final AssignmentSubmitRepo submitRepo;
    private final StudentRepo studentRepo;

    public AssigmentServiceImpl(AssigmentRepo assigmentRepo, BatchRepo batchRepo, StudentEnrollRepo enrollRepo, IStudentService studentService, StudentMapper studentMapper, AssignmentSubmitRepo submitRepo, StudentRepo studentRepo) {
        this.assigmentRepo = assigmentRepo;
        this.batchRepo = batchRepo;
        this.enrollRepo = enrollRepo;
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.submitRepo = submitRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public String addAssigment(AssigmentAddDTO assigmentAddDTO) {
        if (Objects.isNull(assigmentAddDTO) || Objects.isNull(assigmentAddDTO.getAssiUrl())) {
            throw new NotFoundException("Details not provided");
        }
        if (assigmentRepo.existsByAssiCode(assigmentAddDTO.getAssiCode())) {
            throw new AlreadyExistException("Assigment Already exists");
        }
        try {
            Assigment assigment = Assigment.builder()
                    .assiCode(assigmentAddDTO.getAssiCode())
                    .batchId(assigmentAddDTO.getBatchId())
                    .assiUrl(assigmentAddDTO.getAssiUrl())
//                    .deadLine(assigmentAddDTO.getDeadLine())
                    .deadLine(assigmentAddDTO.getDeadLine())
                    .build();
            assigmentRepo.save(assigment);
            return "Assigment adding success";
        } catch (Exception e) {
            log.error("Error while add assignment: {}", e.getMessage());
            throw new HandleException("Something went wrong while add Assigment ");
        }
    }

    @Override
    public Page<AssigmentResponseDTO> getAllAssigment(Pageable pageable) {
        try {
            Page<Assigment> assigment = assigmentRepo.findAll(pageable);

            return assigment.map(ass -> {
                AssigmentResponseDTO responseDTO = new AssigmentResponseDTO();
                Batch batch = EntityUtils.getEntityDetails(ass.getBatchId(), batchRepo, "Batch");
                responseDTO.setAssiId(ass.getAssiId());
                responseDTO.setAssiCode(ass.getAssiCode());
                responseDTO.setAssiUrl(ass.getAssiUrl());
                responseDTO.setDeadLine(ass.getDeadLine());
                responseDTO.setReleaseBy(ass.getCreateBy());
                responseDTO.setReleaseData(EntityUtils.convertToDateTime(ass.getCreatedDate()));
                responseDTO.setBatchCode(batch.getCode());
                responseDTO.setCourseName(batch.getCourse().getName());

                return responseDTO;
            });
        } catch (Exception e) {
            log.error("Error while fetching assignment: {}", e.getMessage());
            throw new HandleException("Something went wrong during fetching assigment");
        }
    }

    @Override
    public String deleteAssignment(long assId) {
        try {
            if (!assigmentRepo.existsById(assId)) {
                throw new NotFoundException("Assigment not found");
            }
            assigmentRepo.deleteById(assId);
            return "Batch id " + assId + " deleted ";
        } catch (Exception e) {
            log.error("Error while delete assignment: {}", e.getMessage());
            throw new NotFoundException("Batch " + assId + " not found");
        }
    }

    @Override
    public Page<AssignmentResposeDTOforStudent> getAllAssigmentByStudent(Pageable pageable, long studentId) {
        if (studentId <= 0) {
            throw new NotFoundException("Student Id not found");
        }
        StudentResponseDTO studentResponseDTO = studentService.studentFindById(studentId);
        if (studentResponseDTO == null) {
            throw new NotFoundException("Student not found");
        }
        try {
            Student student = studentMapper.studentResponseDTOtoStudent(studentResponseDTO);
            StudentEnroll studentEnroll = enrollRepo.findByStudent(student);
            if (studentEnroll == null) {
                return null;
            }
            Page<Assigment> assigment = assigmentRepo.findAllByBatchId(studentEnroll.getBatch().getBatchId(), pageable);
            System.out.println(assigment.toString());
            return assigment.map(a -> {
                AssignmentResposeDTOforStudent responseDTO = new AssignmentResposeDTOforStudent();
                responseDTO.setAssiId(a.getAssiId());
                responseDTO.setAssiCode(a.getAssiCode());
                responseDTO.setBatchId(a.getBatchId());
                responseDTO.setAssiUrl(a.getAssiUrl());
                responseDTO.setReleaseData(EntityUtils.convertToDateTime(a.getCreatedDate()));
                responseDTO.setDeadLine(a.getDeadLine());

                String grade = submitRepo.findByCode(a.getAssiCode());
                responseDTO.setGrade(grade);
                return responseDTO;
            });
        } catch (Exception e) {
            log.error("Error while fetching assignment: {}", e.getMessage());
            throw new HandleException("Something went wrong during fetching assigment");
        }
    }

    @Override
    public String submitAssigment(SubmitRequestDTO submitRequestDTO) {
        if (Objects.isNull(submitRequestDTO)) {
            throw new NotFoundException("Assignment details not found");
        }
        String submitUrl = submitRequestDTO.getSubmitUrl();
        String assiCode = submitRequestDTO.getAssiCode();
        long studentId = submitRequestDTO.getStudentId();

        if (Objects.isNull(submitUrl)) {
            throw new NotFoundException("Assignment Url not found");
        }

        try {

            if (submitRepo.existsByAssiCodeAndStudentId(assiCode, studentId)) {
                AssigmentSubmit submit = submitRepo.findByAssiCodeAndStudentId(assiCode, studentId);
                submit.setSubmitUrl(submitUrl);
                submitRepo.save(submit);
                return "Submit success";

            } else {
                AssigmentSubmit s = new AssigmentSubmit();
                s.setAssiCode(submitRequestDTO.getAssiCode());
                s.setSubmitUrl(submitRequestDTO.getSubmitUrl());
                s.setBatchId(submitRequestDTO.getBatchId());
                s.setStudentId(submitRequestDTO.getStudentId());
                submitRepo.save(s);
                return "Submit success";
            }
        } catch (Exception e) {
            log.error("Error while submit assignment: {}", e.getMessage());
            throw new HandleException("Something went wrong during submit assigment");
        }

    }

    @Override
    public Page<SubmitResponseForAdmin> getAllAssigmentForAdmin(Pageable pageable) {
        Page<AssigmentSubmit> assigmentSubmits = submitRepo.findAll(pageable);
        return assigmentSubmits.map(a -> {
            SubmitResponseForAdmin forAdmin = new SubmitResponseForAdmin(
                    a.getSubmitId(),
                    a.getAssiCode(),
                    EntityUtils.getEntityDetails(a.getBatchId(), batchRepo, "Batch").getCode(),
                    a.getSubmitUrl(),
                    EntityUtils.getEntityDetails(a.getStudentId(), studentRepo, "Student").getFirstName(),
                    a.getGrade()
            );
            return forAdmin;
        });
    }

    @Override
    public String addGrade(SubmitGradeDTO submitGradeDTO) {
        long submitId = submitGradeDTO.getSubmitId();
        AssigmentSubmit assigment = EntityUtils.getEntityDetails(submitId, submitRepo, "Assigment");
        try {
            assigment.setGrade(submitGradeDTO.getGrade());
            String assiGrade = submitRepo.save(assigment).getGrade();
            return assiGrade;
        }catch (RuntimeException e){
            log.error("Error while submit grade: {}", e.getMessage());
            throw new HandleException("Something went wrong during submit grade");
        }
    }

}
