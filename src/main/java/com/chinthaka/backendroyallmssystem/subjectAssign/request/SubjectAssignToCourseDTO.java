package com.chinthaka.backendroyallmssystem.subjectAssign.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssignToCourseDTO {
    private long courseId;
    private List<Long> subjectIds;
}
