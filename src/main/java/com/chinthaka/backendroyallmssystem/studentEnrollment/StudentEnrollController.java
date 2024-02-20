package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.student.response.StudentResponseDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.EnrollPaginationDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
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

    @GetMapping(value = "/find",params = "id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllStudentByBatch(
            @RequestParam("id") long batchId,
            @PageableDefault(sort = "enrollId",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/student/find/pagination");
        Page<EnrollPaginationDTO> response = enrollService.getAllStudentByBatch(batchId,pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

}
