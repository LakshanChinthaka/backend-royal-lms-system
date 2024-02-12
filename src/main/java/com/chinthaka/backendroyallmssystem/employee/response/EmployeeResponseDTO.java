package com.chinthaka.backendroyallmssystem.employee.response;

import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.employee.EmployeeType;
import com.chinthaka.backendroyallmssystem.gender.Gender;
import com.chinthaka.backendroyallmssystem.qualification.Qualification;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNo;
    private Gender gender;
    private String dob;
    private boolean activeStatus;
    private EmployeeType employeeType;
    private String experince;
    private Address address;
    private long qualificationId;
    private String qualification;

}
