package com.chinthaka.backendroyallmssystem.student.response;

import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import com.chinthaka.backendroyallmssystem.gender.Gender;

import com.chinthaka.backendroyallmssystem.studentEnrollment.request.StudentEnrollDTO;
import com.chinthaka.backendroyallmssystem.studentEnrollment.response.StudentEnrollResponseDTO;
import lombok.Data;

@Data
public class StudentResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNo;
    private Gender gender;
    private String dob;
    private boolean activeStatus;
    private String imageUrl;
    private Address address;
    private StudentEnrollResponseDTO enroll;
//    private long enrollId;
//    private BatchResponseDTO batch;
}
