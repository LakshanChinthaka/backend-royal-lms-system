package com.chinthaka.backendroyallmssystem.subject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAddDTO {
    private String subjectCode;
    private String name;
}
