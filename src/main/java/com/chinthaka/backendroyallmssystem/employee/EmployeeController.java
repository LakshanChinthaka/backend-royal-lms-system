package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private final IEmployeeService employeeService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> addEmployee(@RequestBody @Valid EmployeeSaveDTO employeeSaveDTO){
        final String response = employeeService.addEmployee(employeeSaveDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }


    @GetMapping(value = "/find-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> employeeFindById(@RequestParam("id") long empId){
        EmployeeResponseDTO response = employeeService.employeeFindById(empId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping(value = "/edit-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> uploadEmployeeById(
            @RequestParam("id") long empId,@RequestBody EmployeeSaveDTO employeeSaveDTO){
        final String response = employeeService.uploadEmployeeById(employeeSaveDTO,empId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-id",params = {"id"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteEmployee(@RequestParam("id") long empId){
        final String response = employeeService.deleteEmployee(empId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

}
