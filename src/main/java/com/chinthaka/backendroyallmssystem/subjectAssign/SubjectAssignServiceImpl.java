package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.subject.Subject;
import com.chinthaka.backendroyallmssystem.subject.SubjectRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectAssignServiceImpl implements ISubjectAssignService {

    private final SubjectAssignRepo subjectAssignRepo;
    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;

    @Override
    public String assignSubjectToCourse(SubjectAssignToCourseDTO subjectAssignToCourseDTO) {
        Course course = EntityUtils.getEntityDetails(
                subjectAssignToCourseDTO.getCourseId(), courseRepo, "Course");
        if (subjectAssignToCourseDTO.getSubjectIds().isEmpty()){
            throw new NotFoundException("Subjects are not provided ");
        }
        for (Long stId : subjectAssignToCourseDTO.getSubjectIds()) {
            if (!EntityUtils.isEntityExist(stId, subjectRepo)) {
                throw new NotFoundException("Subject id: " + stId + " not found");
            }
        }
       try {
           log.info("Start subject fetch subject from database");
           List<Subject> subjectList = subjectRepo.findAllById(subjectAssignToCourseDTO.getSubjectIds());
           for (Subject s : subjectList) {
               SubjectAssignToCourse sc = new SubjectAssignToCourse();
               sc.setCourse(course);
               sc.setSubjects(s);
               subjectAssignRepo.save(sc);
           }
           return "Subject Successfully Assigned";
       }catch (Exception e){
           log.error("Error while subject assign to course {}", e.getMessage());
           throw new HandleException("Something went wrong subject Assign to course");
       }
    }
}
