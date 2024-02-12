package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/assign")
public class SubjectAssignController {

    private final ISubjectAssignService subjectAssignService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> assignSubjectToCourse(
            @RequestBody SubjectAssignToCourseDTO subjectAssignToCourseDTO) {
        final String response = subjectAssignService.assignSubjectToCourse(subjectAssignToCourseDTO);
        return new ResponseEntity<>(
                new StandardResponse(200, "Success", response), HttpStatus.CREATED);
    }
}

//    @DeleteMapping(value = "/remove",params = {"id"})
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<StandardResponse> editSchool(
//            @RequestParam("id") long subjectId){
//        final String response = subjectAssignService.editSchool(schoolId,schoolDTO);
//        return new ResponseEntity<>(
//                new StandardResponse(200,"Success",response), HttpStatus.OK);
//    }
//}
