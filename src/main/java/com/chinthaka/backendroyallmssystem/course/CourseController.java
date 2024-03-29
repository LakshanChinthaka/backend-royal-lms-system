package com.chinthaka.backendroyallmssystem.course;


import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseForDrop;
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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/course")
@Slf4j
public class CourseController {

    private final ICourseService courseService;
    private final Counter apiRequestCounter;

    @Autowired
    public CourseController(ICourseService courseService, Counter apiRequestCounter) {
        this.courseService = courseService;
        this.apiRequestCounter = apiRequestCounter;
    }

    @PostMapping(value = "/add",params = "imageUrl")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> createCourse(
            @RequestParam String imageUrl,
            @RequestBody CourseDTO courseDTO){
        apiRequestCounter.increment();
        log.info("POST request received on /api/v1/course/add/ {}", imageUrl);
        final String response = courseService.createCourse(courseDTO,imageUrl);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find",params = {"id"})
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> courseGetById(@RequestParam("id") long courseId){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/course/find/{}", courseId);
        CourseResponseDTO response = courseService.courseGetById(courseId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllCourse(
            @PageableDefault(sort = "courseId",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/course/find-all");
        apiRequestCounter.increment();
        Page<CourseResponseDTO> response = courseService.getAllCourse(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(value = "/find-with-name")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAll(){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/course/find");
        List<CourseResponseForDrop> response = courseService.getAll();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
    @PutMapping(value = "/edit",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> uploadCourse(
            @RequestParam("id") long courseId,@RequestBody CourseEditDTO courseEditDTO){
        apiRequestCounter.increment();
        log.info("PUT request received on /api/v1/course/edit/{}", courseId);
        final String response = courseService.uploadCourse(courseEditDTO,courseId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteCourse(@RequestParam("id") long courseId){
        apiRequestCounter.increment();
        log.info("DELETE request received on /api/v1/course/delete/{}", courseId);
        final String response = courseService.deleteStudent(courseId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    // get Enum data
    @GetMapping("/type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getCourseTypeValues() {
        apiRequestCounter.increment();
        List<Type> types = Arrays.asList(Type.values());
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",types), HttpStatus.OK);
    }



}
