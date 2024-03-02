package com.chinthaka.backendroyallmssystem.subject;


import com.chinthaka.backendroyallmssystem.subject.request.SubjectAddDTO;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
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
@Slf4j
@RequestMapping("api/v1/subject")
public class SubjectController {

    private final ISubjectService subjectService;
    public final Counter apiRequestCounter;

    public SubjectController(ISubjectService subjectService, Counter apiRequestCounter) {
        this.subjectService = subjectService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addSubject(@RequestBody SubjectAddDTO subjectAddDTO){
        apiRequestCounter.increment();
        final String response = subjectService.addSubject(subjectAddDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> subjectGetById(@RequestParam("id") long subjectId){
        apiRequestCounter.increment();
        SubjectDTO response = subjectService.subjectGetById(subjectId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteSubject(@RequestParam("id") long subjectId){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/subject/delete/{}",subjectId);
        final String response = subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping(value = "/edit",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> editSubject(
            @RequestParam("id") long subjectId, @RequestBody SubjectDTO subjectDTO){
        apiRequestCounter.increment();
        final String response = subjectService.editSubject(subjectId,subjectDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllSubject(
            @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/subject/find/pageable");
        Page<SubjectDTO> response = subjectService.getAllSubject(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
