package com.chinthaka.backendroyallmssystem.statics.adminStatic;

import com.chinthaka.backendroyallmssystem.assignment.AssigmentRepo;
import com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit.AssignmentSubmitRepo;
import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.employee.EmployeeRepo;
import com.chinthaka.backendroyallmssystem.school.SchoolRepo;
import com.chinthaka.backendroyallmssystem.student.StudentRepo;
import com.chinthaka.backendroyallmssystem.studentEnrollment.StudentEnrollRepo;
import com.chinthaka.backendroyallmssystem.subject.SubjectRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminStaticImpl implements IAdminStatic{

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;
    private final EmployeeRepo employeeRepo;
    private final SchoolRepo schoolRepo;
    private final AssignmentSubmitRepo submitRepo;
    private final AssigmentRepo assigmentRepo;
    private final StudentEnrollRepo enrollRepo;


    @Override
    public AdminStaticResponseDTO getAllStatics() {
        List<Object[]> submitGrade = submitRepo.countByGrade();

        //Overall Pass Rate
        long failAndRepeatCount = submitRepo.countFailAss();
        long totalAssigment = submitRepo.count();
        log.info("Total Count: {}, Fail Count: {}", totalAssigment, failAndRepeatCount);
        long OverallPassRate = ((totalAssigment - failAndRepeatCount) * 100) / totalAssigment;
        log.info("Pass Rate: {}", OverallPassRate);

        double meritCount = 0;
        double distinctionCount = 0;
        double passCount = 0;
        double repeatCount = 0;
        double pendingCount = 0;

        for (Object[] grade : submitGrade) {
            String gradeValue = (String) grade[0];
            BigInteger count = (BigInteger) grade[1]; // Use BigInteger instead of long
            switch (gradeValue) {
                case "Merit":
                    long mCount = count.longValue(); // Convert to long
                    meritCount = (((double) mCount / totalAssigment) * 100);
                    break;
                case "Distinction":
                    long dCount = count.longValue();
                    distinctionCount =  (((double) dCount / totalAssigment) * 100);
                    break;
                case "Pass":
                    long pCount = count.longValue();
                    passCount =  (((double) pCount / totalAssigment) * 100);
                    break;
                case "Repeat":
                    long rCount = count.longValue(); // Convert to long
                    repeatCount =  (((double) rCount / totalAssigment) * 100);
                    break;
                case "Pending":
                    long peCount = count.longValue(); // Convert to long
                    pendingCount =  (((double) peCount / totalAssigment) * 100);
                    break;
            }
        }

        log.info("Merit Count: {}", format("%.2f", meritCount));
        log.info("Distinction Count: {}", format("%.2f", distinctionCount));
        log.info("Pass Count: {}", format("%.2f", passCount));
        log.info("Repeat Count: {}", format("%.2f", repeatCount));
        log.info("Pending Count: {}", format("%.2f", pendingCount));

        //Top Course
        List<Long[]>  topCourse = enrollRepo.topCourse();
        List<String> topCourseName = new ArrayList<>();

        for (Long[] course : topCourse){
            log.info("Each Top ID {}", course[0]);
            Course topC = EntityUtils.getEntityDetails(course[0],courseRepo,"Course");
            topCourseName.add(topC.getName());
            log.info("Top Course {}", topC.getName());
            log.info("Top Map Course {}",topCourseName);

        }
        log.info("Top Course Name: {}", topCourseName);

        //Total Count
        long studentCount = studentRepo.count();
        long employeeCount = employeeRepo.count();
        long courseCount = courseRepo.count();
        long subjectCount = subjectRepo.count();
        long schoolCount = schoolRepo.count();
        long assignmentCount = assigmentRepo.count();
        AdminStaticResponseDTO ad = new AdminStaticResponseDTO(
                studentCount,
                employeeCount,
                subjectCount,
                schoolCount,
                courseCount,
                assignmentCount,
                submitGrade,
                OverallPassRate,
                //Convert to String and round to 2 decimal
                format("%.2f", passCount),
                format("%.2f", meritCount),
                format("%.2f", distinctionCount),
                format("%.2f", repeatCount),
                format("%.2f", pendingCount),
                topCourseName
//                topCourse
        );
        return ad;
    }
}
