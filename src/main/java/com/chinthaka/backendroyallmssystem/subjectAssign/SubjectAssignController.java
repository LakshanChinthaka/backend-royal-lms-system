package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/assign")
public class SubjectAssignController {

    private final ISubjectAssignService subjectAssignService;
    public final Counter apiRequestCounter;

    public SubjectAssignController(ISubjectAssignService subjectAssignService, Counter apiRequestCounter) {
        this.subjectAssignService = subjectAssignService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> assignSubjectToCourse(
            @RequestBody SubjectAssignToCourseDTO subjectAssignToCourseDTO) {
        apiRequestCounter.increment();
        final String response = subjectAssignService.assignSubjectToCourse(subjectAssignToCourseDTO);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", response), HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/remove", params = {"subjectId", "courseId"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> removeAssignSubject(
            @RequestParam("subjectId") long subjectId, @RequestParam("courseId") long courseId) {
        apiRequestCounter.increment();
        final String response = subjectAssignService.removeAssignSubject(subjectId, courseId);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", response), HttpStatus.OK);
    }

    @GetMapping(value = "/find",params = {"id"})
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getSubjectByCourse(@RequestParam("id") long courseId){
        apiRequestCounter.increment();
        List<SubjectAssignResponseDTO> response = subjectAssignService.getSubjectByCourse(courseId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


}