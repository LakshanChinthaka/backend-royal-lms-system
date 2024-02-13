package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;

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
        return studentMapper.studentToStudentResponseDTO(student);
    }

    @Override
    public String uploadStudentById(StudentDTO studentDTO, long studentId) {
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        try {
            final Student convetedStuent = studentMapper.studentSaveDTOtoEntity(studentDTO);
            studentRepo.save(convetedStuent);
            return "Student " + studentId + " Successfully updated";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during upload student details");
        }

    }

    @Override
    public String deleteStudent(long studentId) {
        Student student = EntityUtils.getEntityDetails(studentId, studentRepo, "Student");
        try {
            student.setActiveStatus(false);
            studentRepo.save(student);
            return "Student " + studentId + " Successfully Deleted";
        } catch (Exception e) {
            throw new HandleException("Something went wrong during deleting student details");
        }
    }

}
