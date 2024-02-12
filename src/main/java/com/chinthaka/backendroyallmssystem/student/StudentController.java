package com.chinthaka.backendroyallmssystem.student;

import com.chinthaka.backendroyallmssystem.student.request.StudentDTO;
import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
public class StudentController {


    private final IStudentService studentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addStudent(@RequestBody StudentDTO studentDTO){
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

}
