package com.chinthaka.backendroyallmssystem.statics.adminStatic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStaticResponseDTO {
    long studentCount;
    long employeeCount;
    long subjectCount;
    long schoolCount;
    long courseCount;
    long assignmentCount;
    Object obj;
    long OverallPassRate;
    String passRate;
    String meritRate;
    String distinctionRate;
    String repeatRate;
    String pendingRate;
//    Map<String, Long> topCourseName;
//    List<Long[]>  topCourse;
    List<String> topCourseName;
}
