package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitGradeDTO;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.SubmitRequestDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssigmentResponseDTO;
import com.chinthaka.backendroyallmssystem.assignment.response.AssignmentResposeDTOforStudent;
import com.chinthaka.backendroyallmssystem.assignment.response.SubmitResponseForAdmin;
import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
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
@RequestMapping("api/v1/assigment")
public class AssigmentController {

    private final IAssigmentService assigmentService;

    public AssigmentController(IAssigmentService assigmentService) {
        this.assigmentService = assigmentService;
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addAssigment(
            @RequestBody AssigmentAddDTO assigmentAddDTO){
        log.info("Post request received on /api/v1/assigment/add/{}",assigmentAddDTO.toString());
        final String response = assigmentService.addAssigment(assigmentAddDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }
    @GetMapping(value = "/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllAssigment(
            @PageableDefault(sort = "assiId",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/assigment/find-pageable");
        Page<AssigmentResponseDTO> response = assigmentService.getAllAssigment(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteAssignment(@RequestParam("id") long assId){
        final String response = assigmentService.deleteAssignment(assId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllAssigmentByStudent(
            @PageableDefault(sort = "assiId",direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("id") long studentId){
        log.info("GET request received on /api/v1/assigment/find-by-id-pageable");
        Page<AssignmentResposeDTOforStudent> response = assigmentService.getAllAssigmentByStudent(pageable,studentId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PostMapping(value = "/submit")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<StandardResponse> submitAssigment(
            @RequestBody SubmitRequestDTO submitRequestDTO){
        log.info("Post request received on /api/v1/assigment/submit/{}",submitRequestDTO.toString());
        final String response = assigmentService.submitAssigment(submitRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllAssigmentForAdmin(
            @PageableDefault(sort = "submitId",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/assigment/find-all-pageable");
        Page<SubmitResponseForAdmin> response = assigmentService.getAllAssigmentForAdmin(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    //Grade submit
    @PutMapping(value = "/grade")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addGrade(
            @RequestBody SubmitGradeDTO submitGradeDTO){
        log.info("Put request received on /api/v1/assigment/grade/{}",submitGradeDTO);
        final String response = assigmentService.addGrade(submitGradeDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
