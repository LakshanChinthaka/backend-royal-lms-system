package com.chinthaka.backendroyallmssystem.statics.adminStatic;

import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminStatic {

    private final IAdminStatic adminStaticService;

    @GetMapping(value = "/static")
    public ResponseEntity<StandardResponse> getAllStatics(){
        AdminStaticResponseDTO response = adminStaticService.getAllStatics();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

}
