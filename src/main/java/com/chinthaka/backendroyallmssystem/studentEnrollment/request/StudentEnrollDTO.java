package com.chinthaka.backendroyallmssystem.studentEnrollment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollDTO {
    private long batchId;
    private long studentId;
    private long courseId;
}
