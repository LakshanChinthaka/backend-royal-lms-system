package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.request.StudentImageUploadDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("api/v1/student")
public class StudentController {


    private final IStudentService studentService;
    public final Counter apiRequestCounter;

    public StudentController(IStudentService studentService, Counter apiRequestCounter) {
        this.studentService = studentService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addStudent(@RequestBody StudentDTO studentDTO){
        apiRequestCounter.increment();
        log.info("Start to execute addStudent : {} ", studentDTO);
            final String response = studentService.addStudent(studentDTO);
            return new ResponseEntity<>(
                    new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @PutMapping(value = "/upload-image")
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<StandardResponse> uploadImage(
            @RequestBody StudentImageUploadDTO imageUploadDTO){
        apiRequestCounter.increment();
       log.info("Student profile upload: {}", imageUploadDTO.toString());
        final String response = studentService.uploadImage(imageUploadDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> studentFindById(@RequestParam("id") long studentId){
        apiRequestCounter.increment();
        StudentResponseDTO response = studentService.studentFindById(studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping(value = "/edit-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> uploadStudentById(
            @RequestParam("id") long studentId,@RequestBody StudentDTO studentDTO){
        final String response = studentService.uploadStudentById(studentDTO,studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteStudent(@RequestParam("id") long studentId){
        apiRequestCounter.increment();
        log.info("DELETE request received on /api/v1/student/find/{}", studentId);
        final String response = studentService.deleteStudent(studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllSubject(
            @PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/student/find/pagination");
        Page<StudentResponseDTO> response = studentService.getAllSubject(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-nic",params = {"nic", "role"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findByStudentAndEmpByNic(@RequestParam("nic") String nic,
                                                             @RequestParam("role")String role){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/student/find-by-nic");
        Object response = studentService.findByStudentAndEmpByNic(nic,role);
            return new ResponseEntity<>(
                    new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-email",params = {"nic"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findEmail(@RequestParam("nic")String nic){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/student/find-by-email");
        Object response = studentService.findEmail(nic);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
