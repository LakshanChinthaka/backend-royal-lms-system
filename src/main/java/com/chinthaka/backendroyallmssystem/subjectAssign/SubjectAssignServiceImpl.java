package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.course.CourseRepo;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.subject.Subject;
import com.chinthaka.backendroyallmssystem.subject.SubjectRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.request.SubjectAssignToCourseDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectAssignServiceImpl implements ISubjectAssignService {

    private final SubjectAssignRepo subjectAssignRepo;
    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;

    public SubjectAssignServiceImpl(SubjectAssignRepo subjectAssignRepo, CourseRepo courseRepo, SubjectRepo subjectRepo) {
        this.subjectAssignRepo = subjectAssignRepo;
        this.courseRepo = courseRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public String assignSubjectToCourse(SubjectAssignToCourseDTO subjectAssignToCourseDTO) {

        if (subjectAssignToCourseDTO.getSubjectId() < 0){
            throw new NotFoundException("Subject are not provided ");
        }
        Course course = EntityUtils.getEntityDetails(
                subjectAssignToCourseDTO.getCourseId(), courseRepo, "Course");

        Subject subject = EntityUtils.getEntityDetails(subjectAssignToCourseDTO.getSubjectId(),subjectRepo,"Subject");
        if (subjectAssignRepo.existsAllByCourseAndSubjects(course,subject)){
            throw new AlreadyExistException("Subject Already Assigned to Course id: "+subjectAssignToCourseDTO.getCourseId());
        }
       try {
           log.info("Start subject fetch subject from database");
           SubjectAssignToCourse sub = new SubjectAssignToCourse(
                   0L,
                   subject,
                   course
           );
           subjectAssignRepo.save(sub);
           return "Subject Successfully Assigned";
       }catch (Exception e){
           log.error("Error while subject assign to course: {}", e.getMessage());
           throw new HandleException("Something went wrong subject Assign to course: ");
       }
    }

    @Override
    public String removeAssignSubject(long subjectId, long courseId) {
        Course course = EntityUtils.getEntityDetails(courseId,courseRepo,"Course");
        Subject subject = EntityUtils.getEntityDetails(subjectId,subjectRepo,"Subject");
      try {
          SubjectAssignToCourse foundRecord = subjectAssignRepo.findByCourseAndSubjects(course,subject);
          if (foundRecord != null){
              subjectAssignRepo.deleteById(foundRecord.getAssignId());
          }
            return "Successfully removed";
      }catch (NullPointerException e){
          log.error("Error while fetching assign subject: {} ",e.getMessage());
          throw new NotFoundException("Record not found");
      }catch (Exception e){
          log.error("Error while deleting assign subject: {} ",e.getMessage());
          throw new NotFoundException("Something went wrong while deleting assign subject");
      }
    }

    @Override
    public  List<SubjectAssignResponseDTO> getSubjectByCourse(long courseId) {
        Course course = EntityUtils.getEntityDetails(courseId, courseRepo, "Course");
        try {
            List<SubjectAssignToCourse> subjectList = subjectAssignRepo.findAllByCourse(course);
            return subjectList.stream().map(subject -> {
                SubjectAssignResponseDTO s = new SubjectAssignResponseDTO();
                s.setAssignId(subject.getAssignId());
                s.setSubjectId(subject.getSubjects().getSubjectId());
                s.setCode(subject.getSubjects().getSubjectCode());
                s.setSubject(subject.getSubjects().getName());
                s.setCreateBy(subject.getCreateBy());
                s.setCreatedDate(subject.getCreatedDate());
                s.setModifiedBy(subject.getModifiedBy());
                s.setModifiedData(subject.getModifiedData());
                return s;
            }).collect(Collectors.toList()); // Collect the mapped objects into a list
        } catch (Exception e) {
            log.error("Error while getting assigned subjects: {}", e.getMessage());
            throw new NotFoundException("Something went wrong while fetching assigned subjects");
        }
    }
}
