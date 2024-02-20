package com.chinthaka.backendroyallmssystem.studentEnrollment.response;

import com.chinthaka.backendroyallmssystem.gender.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollPaginationDTO {

    private long enrollId;
    private long batchId;
    private long studentId;
    private String firstName;
    private String lastName;
    private String enrollDate;
    private String nic;
    private Gender gender;
    private String assignBy;
}
