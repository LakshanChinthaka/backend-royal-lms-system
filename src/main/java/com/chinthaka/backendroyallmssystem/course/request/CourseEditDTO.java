package com.chinthaka.backendroyallmssystem.course.request;

import com.chinthaka.backendroyallmssystem.course.Category;
import com.chinthaka.backendroyallmssystem.course.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEditDTO {

    private String name;
    private String description;
    private int totalCredit;
    private int totalHours;
    private Category category;
    private Type courseType;
    private double fees;
    private String duration;
}
