package com.chinthaka.backendroyallmssystem.subject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {

    private Long subjectId;
    private String subjectCode;
    private String name;
    private String createBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedData;
}
