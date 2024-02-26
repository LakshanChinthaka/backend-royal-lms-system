package com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitRequestDTO {
    private long assiId;
    private String assiCode;
    private long studentId;
    private String submitUrl;
    private long batchId;
}
