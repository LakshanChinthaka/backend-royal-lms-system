package com.chinthaka.backendroyallmssystem.batch;

import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/v1/batch")
@CrossOrigin("http://localhost:5173")
public class BatchController {


    private final IBatchService batchService;
    private final Counter apiRequestCounter;

    @Autowired
    public BatchController(IBatchService batchService, Counter apiRequestCounter) {
        this.batchService = batchService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addBatch(
            @RequestBody BatchDTO batchDTO){
        apiRequestCounter.increment();
        final String response = batchService.addBatch(batchDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getById(@RequestParam("id") long batchId){
        apiRequestCounter.increment();
        BatchResponseDTO response = batchService.getById(batchId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteBatch(@RequestParam("id") long batchId){
        apiRequestCounter.increment();
        final String response = batchService.deleteBatch(batchId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-all",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllBatch(
            @PageableDefault(sort = "batchId",direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "id") long courseId){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/batch/find-all");
        Page<BatchResponseDTO> response = batchService.getAllBatch(pageable,courseId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllSubject(
            @PageableDefault(sort = "batchId",direction = Sort.Direction.DESC) Pageable pageable){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/batch/find");
        Page<BatchResponseDTO> response = batchService.getAllBatchData(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
