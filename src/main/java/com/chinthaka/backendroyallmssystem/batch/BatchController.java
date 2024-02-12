package com.chinthaka.backendroyallmssystem.batch;

import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/batch")
public class BatchController {


    private final IBatchService batchService;

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addBatch(
            @RequestBody BatchDTO batchDTO){
        final String response = batchService.addBatch(batchDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getById(@RequestParam("id") long batchId){
        BatchResponseDTO response = batchService.getById(batchId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteBatch(@RequestParam("id") long batchId){
        final String response = batchService.deleteBatch(batchId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
