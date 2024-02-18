package com.chinthaka.backendroyallmssystem.subjectAssign.response;
;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignResponseDTO {
    private long assignId;
    private long subjectId;
    private String code;
    private String subject;
    private String createBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedData;

    public SubjectAssignResponseDTO(long assignId, long subjectId, String code, String subject) {
        this.assignId = assignId;
        this.subjectId = subjectId;
        this.code = code;
        this.subject = subject;
    }
}
