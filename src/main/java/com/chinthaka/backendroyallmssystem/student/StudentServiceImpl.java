package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.batch.BatchRepo;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.employee.Employee;
import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnroll;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollMapper;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.StudentEnrollResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final BatchRepo batchRepo;
    private final CourseRepo courseRepo;
    private final StudentEnrollRepo studentEnrollRepo;
    private final StudentEnrollMapper studentEnrollMapper;
    private final EmployeeRepo employeeRepo;

    @Override
    @Transactional
    public String addStudent(StudentDTO studentDTO) {
        if (null == studentDTO) {
            throw new NotFoundException("Student details not provided");
        }
        if (studentRepo.existsByNic(studentDTO.getNic())) {
            throw new AlreadyExistException("Student Already Exists");
        }

        try {
            Student student = studentMapper.studentSaveDTOtoEntity(studentDTO);

            Address address = new Address();
            address.setAddress(student.getAddress().getAddress());
            address.setCity(student.getAddress().getCity());
            address.setDistrict(student.getAddress().getDistrict());
            student.setAddress(address);

            studentRepo.save(student);
            return "Registration successful";
        } catch (Exception e) {
            log.error("Error while student registration {} ", e.getMessage());
            throw new HandleException("Something went wrong during student registration");
        }
    }


    @Override
    public String uploadImage(String imageUrl, long studentId) {
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        System.out.println(student);
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new NotFoundException("Image not found in url");
        }
        try {
            student.setImageUrl(imageUrl);
            studentRepo.save(student);
            return "Image upload success";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during upload image");
        }
    }

    @Override
    public StudentResponseDTO studentFindById(long studentId) {
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        return getStudentResponseDTO(student);
    }

    @Override
    public String uploadStudentById(StudentDTO studentDTO, long studentId) {
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        try {
            final Student convertedStudent = studentMapper.studentSaveDTOtoEntity(studentDTO);
            convertedStudent.setId(studentId);
            studentRepo.save(convertedStudent);
            return "Student " + studentId + " Successfully updated";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during upload student details");
        }

    }

//    @Override
//    @Transactional
//    public String deleteStudent(long studentId) {
//        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
//        try {
//            if (studentEnrollRepo.existsByStudent(student)){
//                long enroll = studentEnrollRepo.findByStudent(student).getEnrollId();
//                studentEnrollRepo.deleteById(enroll);
//            }
//            studentRepo.deleteById(studentId);
//            return "Student " + studentId + " Successfully Deleted";
//        } catch (Exception e) {
//            log.error("Error while updating student: {}",e.getMessage());
//            throw new HandleException("Something went wrong during deleting student details");
//        }
//    }
@Override
@Transactional
public String deleteStudent(long studentId) {
    // Retrieve the student entity
    Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
    try {
        // Check if there are associated StudentEnroll records
        if (studentEnrollRepo.existsByStudent(student)) {
            // Retrieve the enrollId of the first associated StudentEnroll record
            long enrollId = studentEnrollRepo.findByStudent(student).getEnrollId();
            // Delete the associated StudentEnroll record
            studentEnrollRepo.deleteById(enrollId);
        }
        // Delete the student entity
        studentRepo.deleteById(studentId);
        return "Student " + studentId + " Successfully Deleted";
    } catch (Exception e) {
        log.error("Error while updating student: {}", e.getMessage());
        throw new HandleException("Something went wrong during deleting student details");
    }
}


    @Override
    public Page<StudentResponseDTO> getAllSubject(Pageable pageable) {
        log.info("Student Pagination : {}", pageable);
        try {
            Page<Student> studentPage = studentRepo.findAll(pageable);

            return studentPage.map(student -> {
                StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
                studentResponseDTO.setId(student.getId());
                studentResponseDTO.setFirstName(student.getFirstName());
                studentResponseDTO.setLastName(student.getLastName());
                studentResponseDTO.setNic(student.getNic());
                studentResponseDTO.setMobileNo(student.getMobileNo());
                studentResponseDTO.setGender(student.getGender());
                studentResponseDTO.setDob(student.getDob());
                studentResponseDTO.setActiveStatus(student.isActiveStatus());
                studentResponseDTO.setImageUrl(student.getImageUrl());
                studentResponseDTO.setAddress(student.getAddress());

                StudentEnroll enrollData = studentEnrollRepo.findByStudent(student);

                if (enrollData != null) {
                    StudentEnrollResponseDTO enrollDetails = new StudentEnrollResponseDTO(
                            enrollData.getEnrollId(),
                            enrollData.getBatch().getBatchId(),
                            enrollData.getBatch().getCode(),
                            enrollData.getCourse().getCourseId(),
                            enrollData.getCourse().getName(),
                            enrollData.getCreatedDate() // Assuming enrollDate is the correct field name
                    );
                    studentResponseDTO.setEnroll(enrollDetails);
                }

                return studentResponseDTO;
            });
        } catch (Exception e) {
            log.error("Error while fetching student details: {}", e.getMessage());
            throw new HandleException("Error while fetching student details");
        }
    }

    @Override
    public StudentResponseDTO studentFindByNic(String nic) {
        if (nic.isBlank()){
            throw new NotFoundException("NIC number is missing");
        }

        Student student = studentRepo.findByNic(nic);
        if (Objects.isNull(student)){
            throw new NotFoundException("Student not found");
        };
        return getStudentResponseDTO(student);
    }

    private StudentResponseDTO getStudentResponseDTO(Student student) {
        StudentEnroll enrollData = studentEnrollRepo.findByStudent(student);

        StudentEnrollResponseDTO enrollDetails = null;
        if (Objects.nonNull(enrollData)) {
            enrollDetails = new StudentEnrollResponseDTO(
                    enrollData.getEnrollId(),
                    enrollData.getBatch().getBatchId(),
                    enrollData.getBatch().getCode(),
                    enrollData.getCourse().getCourseId(),
                    enrollData.getCourse().getName(),
                    enrollData.getCreatedDate()
            );
        }
        StudentResponseDTO st = studentMapper.studentToStudentResponseDTO(student);
        st.setEnroll(enrollDetails);
        return st;
    }

    @Override
    public Object findByStudentAndEmpByNic(String nic, String role) {
        if (!role.isEmpty()){
            if (Objects.equals(role,"STUDENT")){
                Student student = studentRepo.findByNic(nic);
                if (Objects.isNull(student)){
                   throw new NotFoundException("Student not found");
                }
                return student;
            }else {
                Employee emp = employeeRepo.findByNic(nic);
                if (Objects.equals(emp,null)){
                    throw new NotFoundException("Employee not found");
                }
                return emp;
            }
        }
        throw new NotFoundException("First select student or employee");
    }
}
