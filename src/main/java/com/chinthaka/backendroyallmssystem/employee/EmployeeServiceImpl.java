package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.employee.request.EmployeeSaveDTO;
import com.chinthaka.backendroyallmssystem.employee.request.ImageUploadDTO;
import com.chinthaka.backendroyallmssystem.employee.response.EmployeeResponseDTO;
import com.chinthaka.backendroyallmssystem.employee.response.ImageGetDTO;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {


    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;
    private final Counter status200Counter;
    private final Counter status400Counter;
    private final Counter status404Counter;
    private final Counter status500Counter;

    @Override
    public String addEmployee(EmployeeSaveDTO employeeSaveDTO) {
        EntityUtils.isEmpty(employeeSaveDTO);

        if (employeeRepo.existsByNic(employeeSaveDTO.getNic())) {
            status400Counter.increment();
            throw new AlreadyExistException("Employee Already Exist");
        }
        try {
            log.info("Start mapping employee DTO to entity");
            Employee employee = employeeMapper.employeeSaveDTOtoEmployee(employeeSaveDTO);
            employeeRepo.save(employee);
            status200Counter.increment();
            return "Employee successfully created";
        } catch (Exception e) {
            log.error("Error while creating employee: {}", e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong during creating employee");
        }
    }

    @Override
    public EmployeeResponseDTO employeeFindById(long empId) {
        log.info("Execute employee findById method");
        final Employee employee = EntityUtils.getEntityDetails(empId, employeeRepo, "Employee");
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
        status500Counter.increment();
        return employeeResponseDTO;
    }

    @Override
    public String updateEmployeeById(EmployeeSaveDTO employeeSaveDTO, long empId) {
        return null;
    }

    @Override
    public String updateImageUrl(ImageUploadDTO imageUploadDTO) {
        log.info("Start Execute updateImageUrl: {}", imageUploadDTO.getImageUrl());
        Employee employee = EntityUtils.getEntityDetails(imageUploadDTO.getEmployeeId(), employeeRepo, "Employee");
        try {
            employee.setImageUrl(imageUploadDTO.getImageUrl());
            log.info("Set imageURL");
            employeeRepo.save(employee);
            status200Counter.increment();
            return "Image upload ok";
        } catch (Exception e) {
            log.error("Error while uploading image: {}", e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong during uploading image");
        }
    }

    @Override
    public ImageGetDTO getImage(long empId) {
        Employee employee = EntityUtils.getEntityDetails(empId, employeeRepo, "Employee");
        log.info("Employee Details: {}", employee.toString());
        try {
            return new ImageGetDTO(employee.getImageUrl());
        } catch (Exception e) {
            log.error("Error while get image: {}", e.getMessage());
            status500Counter.increment();
            throw new HandleException("Something went wrong during getting image");
        }
    }


    @Override
    public String deleteEmployee(long empId) {
        return null;
    }
}
