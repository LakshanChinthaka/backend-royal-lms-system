package com.chinthaka.backendroyallmssystem.course.response;


import com.chinthaka.backendroyallmssystem.course.Category;
import com.chinthaka.backendroyallmssystem.course.Medium;
import com.chinthaka.backendroyallmssystem.course.Type;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


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
    private String imageUrl;
    private String schoolCode;
    private String createBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedData;
    private List<SubjectAssignResponseDTO> subjectlist;

    public void setSchool(School school) {
        this.schoolName = school.getSchoolName();
        this.schoolCode = school.getSchoolCode();
    }
}
