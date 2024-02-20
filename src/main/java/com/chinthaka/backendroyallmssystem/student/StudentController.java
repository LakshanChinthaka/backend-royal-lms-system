package com.chinthaka.backendroyallmssystem.student;


import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/student")
//@CrossOrigin("http://localhost:5173")
public class StudentController {


    private final IStudentService studentService;
    private final EmployeeRepo employeeRepo;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addStudent(@RequestBody StudentDTO studentDTO){
        log.info("Start to execute addStudent : {} ", studentDTO);
            final String response = studentService.addStudent(studentDTO);
            return new ResponseEntity<>(
                    new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @PutMapping(value = "/upload-image",params = {"imageUrl","id"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<StandardResponse> uploadImage(
            @RequestParam("imageUrl") String imageUrl,@RequestParam("id") long studentId){
        System.out.println(imageUrl);
        System.out.println(studentId);
        final String response = studentService.uploadImage(imageUrl,studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> studentFindById(@RequestParam("id") long studentId){
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
        final String response = studentService.deleteStudent(studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllSubject(
            @PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/student/find/pagination");
        Page<StudentResponseDTO> response = studentService.getAllSubject(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-nic",params = {"nic", "role"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findByStudentAndEmpByNic(@RequestParam("nic") String nic,
                                                             @RequestParam("role")String role){
        log.info("GET request received on /api/v1/student/find-by-nic");
        Object response = studentService.findByStudentAndEmpByNic(nic,role);
            return new ResponseEntity<>(
                    new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-email",params = {"nic"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findEmail(@RequestParam("nic")String nic){
        log.info("GET request received on /api/v1/student/find-by-email");
        Object response = studentService.findEmail(nic);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
