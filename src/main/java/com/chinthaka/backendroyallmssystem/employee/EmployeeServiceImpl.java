package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {


    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    @Override
    public String addEmployee(EmployeeSaveDTO employeeSaveDTO) {
        EntityUtils.isEmpty(employeeSaveDTO);

        if (employeeRepo.existsByNic(employeeSaveDTO.getNic())) {
            throw new AlreadyExistException("Employee Already Exist");
        }
        try {
            log.info("Start mapping employee DTO to entity");
            Employee employee = employeeMapper.employeeSaveDTOtoEmployee(employeeSaveDTO);
            employeeRepo.save(employee);
            return "Employee successfully created";
        } catch (Exception e) {
            log.error("Error while creating employee: {}", e.getMessage());
            throw new HandleException("Something went wrong during creating employee");
        }
    }

    @Override
    public EmployeeResponseDTO employeeFindById(long empId) {
        log.info("Execute employee findById method");
        final Employee employee = EntityUtils.getEntityDetails(empId, employeeRepo, "Employee");
//        EmployeeResponseDTO employeeResponseDTO = employeeMapper.employeeToEmployeeResponseDTO(employee);
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getNic(),
                employee.getMobileNo(),
                employee.getGender(),
                employee.getDob(),
                employee.isActiveStatus(),
                employee.getEmployeeType(),
                employee.getExperince(),
                employee.getAddress(),
                employee.getQualification().getQualificationId(),
                employee.getQualification().getQualification()
        );
        return employeeResponseDTO;
    }

    @Override
    public String updateEmployeeById(EmployeeSaveDTO employeeSaveDTO, long empId) {
        return null;
    }

    @Override
    public String deleteEmployee(long empId) {
        return null;
    }
}
