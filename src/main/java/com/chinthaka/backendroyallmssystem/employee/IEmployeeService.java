package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.request.ImageUploadDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;
import com.chinthaka.backendroyallmssystem.employee.response.ImageGetDTO;

public interface IEmployeeService {

    String addEmployee(EmployeeSaveDTO employeeSaveDTO);

    EmployeeResponseDTO employeeFindById(long empId);

    String deleteEmployee(long empId);

    String updateEmployeeById(EmployeeSaveDTO employeeSaveDTO, long empId);

    String updateImageUrl(ImageUploadDTO imageUploadDTO);

    ImageGetDTO getImage(long empId);
}
