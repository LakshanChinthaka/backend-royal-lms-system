package com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitGradeDTO {
    private long submitId;
    private String grade;
}
