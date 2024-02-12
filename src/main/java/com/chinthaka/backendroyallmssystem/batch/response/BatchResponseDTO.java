package com.chinthaka.backendroyallmssystem.batch.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponseDTO {
    private Long batchId;
    private String code;
    private String courseName;
    private String courseCode;
    private String schoolName;
    private String schoolCode;
}
