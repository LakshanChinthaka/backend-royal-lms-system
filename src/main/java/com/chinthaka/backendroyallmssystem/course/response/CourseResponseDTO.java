package com.chinthaka.backendroyallmssystem.course.response;

import com.chinthaka.backendroyallmssystem.course.Category;
import com.chinthaka.backendroyallmssystem.course.Medium;
import com.chinthaka.backendroyallmssystem.course.Type;
import com.chinthaka.backendroyallmssystem.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
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
    private String schoolName;
    private String schoolCode;

    public void setSchool(School school) {
        this.schoolName = school.getSchoolName();
        this.schoolCode = school.getSchoolCode();
    }
}
