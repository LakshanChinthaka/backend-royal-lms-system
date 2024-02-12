package com.chinthaka.backendroyallmssystem.subjectAssign.response;
;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectAssignResponseDTO {
    private long assignId;
    private long subjectId;
    private String code;
    private String subject;
}
