package com.chinthaka.backendroyallmssystem.school.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponseDTO {
    private Long schoolID;

    private String schoolName;

    private String schoolCode;

    private String createBy;

    private boolean activeStatus;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedData;
}
