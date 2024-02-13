package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enroll")
public class StudentEnrollController {

    private final IStudentEnrollService enrollService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> studentEnroll(
            @RequestBody StudentEnrollDTO studentEnrollDTO) {
        final String response = enrollService.studentEnroll(studentEnrollDTO);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", response), HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/remove", params = {"studentId"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> removeStudent(
            @RequestParam("studentId") long studentId) {
        final String response = enrollService.removeStudent(studentId);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", response), HttpStatus.OK);
    }

}
