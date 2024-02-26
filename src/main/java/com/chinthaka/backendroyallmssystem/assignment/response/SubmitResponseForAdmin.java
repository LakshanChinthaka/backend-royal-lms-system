package com.chinthaka.backendroyallmssystem.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResponseForAdmin {

    private Long submitId;
    private String assiCode;
    private String batchCode;
    private String assiUrl;
    private String studentName;
    private String grade;
}
