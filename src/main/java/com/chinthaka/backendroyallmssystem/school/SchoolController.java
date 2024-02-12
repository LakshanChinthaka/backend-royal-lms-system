package com.chinthaka.backendroyallmssystem.school;

import com.chinthaka.backendroyallmssystem.school.request.SchoolDTO;
import com.chinthaka.backendroyallmssystem.school.response.SchoolResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/school")
public class SchoolController {

    private final ISchoolService schoolService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addSchool(@RequestBody SchoolDTO schoolDTO){
        final String response = schoolService.addSchool(schoolDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> schoolGetById(@RequestParam("id") long schoolId){
        SchoolDTO response = schoolService.subjectGetById(schoolId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findAllSchools(){
        List<SchoolResponseDTO> response = schoolService.findAllSchools();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteSchool(@RequestParam("id") long schoolId){
        final String response = schoolService.deleteSchool(schoolId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping(value = "/edit",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> editSchool(
            @RequestParam("id") long schoolId, @RequestBody SchoolDTO schoolDTO){
        final String response = schoolService.editSchool(schoolId,schoolDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
