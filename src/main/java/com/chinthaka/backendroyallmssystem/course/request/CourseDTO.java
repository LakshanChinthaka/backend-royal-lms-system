package com.chinthaka.backendroyallmssystem.course.request;

import com.chinthaka.backendroyallmssystem.course.Category;
import com.chinthaka.backendroyallmssystem.course.Medium;
import com.chinthaka.backendroyallmssystem.course.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long courseId;
    private String code;
    private String name;
    private String description;
    private int totalCredit;
    private int totalHours;
    private Category category;
    private Type courseType;
    private double fees;
    private String duration;
    private Medium medium;
    private int schoolId;
//    private String imageUrl;

}
