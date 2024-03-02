package com.chinthaka.backendroyallmssystem.school;

import com.chinthaka.backendroyallmssystem.school.request.SchoolDTO;
import com.chinthaka.backendroyallmssystem.school.response.SchoolResponseDTO;
import com.chinthaka.backendroyallmssystem.statics.EndpointAccessStatistics;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/v1/school")
@CrossOrigin("http://localhost:5173")
public class SchoolController {

    private final ISchoolService schoolService;
    public final Counter apiRequestCounter;


    public SchoolController(ISchoolService schoolService, Counter apiRequestCounter) {
        this.schoolService = schoolService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addSchool(@RequestBody SchoolDTO schoolDTO){
        apiRequestCounter.increment();
        final String response = schoolService.addSchool(schoolDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> schoolGetById(@RequestParam("id") long schoolId){
        apiRequestCounter.increment();
        SchoolDTO response = schoolService.subjectGetById(schoolId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> findAllSchools(){
        List<SchoolResponseDTO> response = schoolService.findAllSchools();
        apiRequestCounter.increment();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteSchool(@RequestParam("id") long schoolId){
        apiRequestCounter.increment();
        final String response = schoolService.deleteSchool(schoolId);
        apiRequestCounter.increment();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping(value = "/edit",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> editSchool(
            @RequestParam("id") long schoolId, @RequestBody SchoolDTO schoolDTO){
        apiRequestCounter.increment();
        final String response = schoolService.editSchool(schoolId,schoolDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
