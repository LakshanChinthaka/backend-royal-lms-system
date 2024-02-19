package com.chinthaka.backendroyallmssystem.course.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseForDrop {

    private Long courseId;
    private String code;
    private String name;
}
