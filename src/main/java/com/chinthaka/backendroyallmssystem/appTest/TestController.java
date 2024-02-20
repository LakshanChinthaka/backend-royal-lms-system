package com.chinthaka.backendroyallmssystem.appTest;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.batch.BatchRepo;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final StudentRepo studentRepo;
    private final StudentEnrollRepo enrollRepo;
    private final BatchRepo batchRepo;

    @GetMapping(value = "/count",params = "id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public int getCount(@RequestParam("id") long id){
        Batch batch = EntityUtils.getEntityDetails(id,batchRepo,"Batch");
        return enrollRepo.count(batch);
    }
}
