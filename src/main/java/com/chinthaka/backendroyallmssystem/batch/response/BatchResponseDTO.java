package com.chinthaka.backendroyallmssystem.batch.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String createBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedData;
    private boolean activeStatus;
    private int count;
}
