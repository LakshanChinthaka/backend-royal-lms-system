package com.chinthaka.backendroyallmssystem.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResposeDTOforStudent {
    private Long assiId;;
    private String assiCode;
    private long batchId;
    private String assiUrl;
    private String deadLine;
    private String releaseData;
    private String grade;
    private String submitDate;
}
