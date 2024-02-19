package com.chinthaka.backendroyallmssystem.enumsAPI;

import com.chinthaka.backendroyallmssystem.course.Category;
import com.chinthaka.backendroyallmssystem.course.Medium;
import com.chinthaka.backendroyallmssystem.course.Type;
import com.chinthaka.backendroyallmssystem.gender.Gender;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enum")
@CrossOrigin("http://localhost:5173")
public class EnumController {

    @GetMapping("/category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getCourseCategory() {
        List<Category> response = Arrays.asList(Category.values());
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping("/type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getCourseTypes() {
        List<Type> response = Arrays.asList(Type.values());
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping("/medium")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getCourseMedium() {
        List<Medium> response = Arrays.asList(Medium.values());
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}

