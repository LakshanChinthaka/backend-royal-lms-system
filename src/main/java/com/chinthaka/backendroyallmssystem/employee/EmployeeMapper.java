package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper employeeMapper = Mappers.getMapper( EmployeeMapper.class );

//    EmployeeResponseDTO employeeToEmployeeResponseDTO(Employee employee);

    Employee employeeSaveDTOtoEmployee(EmployeeSaveDTO employeeSaveDTO);

    Object employeeToEmployeeResponseDTO(Employee employee);
}
