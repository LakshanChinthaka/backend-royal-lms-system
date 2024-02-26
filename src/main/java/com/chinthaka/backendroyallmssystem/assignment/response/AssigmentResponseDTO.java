package com.chinthaka.backendroyallmssystem.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssigmentResponseDTO {
    private Long assiId;;
    private String assiCode;
    private String assiUrl;
    private String deadLine;
    private String releaseData;
    private String releaseBy;
    private String batchCode;
    private String courseName;
}
