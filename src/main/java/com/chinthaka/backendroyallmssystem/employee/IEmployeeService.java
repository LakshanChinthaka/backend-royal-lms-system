package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;

public interface IEmployeeService {

    String addEmployee(EmployeeSaveDTO employeeSaveDTO);

    EmployeeResponseDTO employeeFindById(long empId);

    String deleteEmployee(long empId);

    String updateEmployeeById(EmployeeSaveDTO employeeSaveDTO, long empId);
}
