package com.chinthaka.backendroyallmssystem.student.request;

import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.gender.Gender;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNo;
    private Gender gender;
    private String dob;
    private boolean activeStatus;
    private Address address;
}
