package com.chinthaka.backendroyallmssystem.studentEnrollment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollResponseDTO {
    private long enrollId;
    private long batchId;
    private String batchCode;
    private long courseId;
    private String courseName;
    private LocalDateTime enrollDate;
}
