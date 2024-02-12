package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.school.SchoolRepo;
import com.chinthaka.backendroyallmssystem.subject.request.SubjectDTO;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignToCourse;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourserServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final SchoolRepo schoolRepo;
    private final SubjectAssignRepo subjectAssignRepo;

    @Autowired
    public CourserServiceImpl(CourseRepo courseRepo, CourseMapper courseMapper, SchoolRepo schoolRepo, SubjectAssignRepo subjectAssignRepo) {
        this.courseRepo = courseRepo;
        this.courseMapper = courseMapper;
        this.schoolRepo = schoolRepo;
        this.subjectAssignRepo = subjectAssignRepo;
    }

    @Override
    public String createCourse(CourseDTO courseDTO) {
        if (courseDTO == null){
            throw new AlreadyExistException("Course details not provide");
        }
        if (EntityUtils.isCourseExist(courseDTO.getCourseId(),courseRepo)
        && courseRepo.findByCode(courseDTO.getCourseId())){
            throw new AlreadyExistException(
                    "Course " + courseDTO.getCourseId() +" Already Exist");
        }
        try {
            Course course = courseMapper.courseSaveDTOtoCourse(courseDTO);
            School school = EntityUtils.getEntityDetails(courseDTO.getSchoolId(),schoolRepo,"School");
            course.setSchool(school);
            courseRepo.save(course);
            return "Course successfully created";
        }catch (Exception e){
            throw new HandleException("Something went wrong during creating course");
        }
    }

    @Override
    public CourseResponseDTO courseGetById(long courseId) {
        log.info("Start get course details by course id: {} ",courseId);
        final Course course = EntityUtils.getEntityDetails(courseId,courseRepo,"Course");
        School school = EntityUtils.getEntityDetails(
                course.getSchool().getSchoolID(),schoolRepo,"School");
     try {
         CourseResponseDTO courseResponseDTO = courseMapper.courseToCourseResponseDTO(course);
         courseResponseDTO.setSchoolCode(school.getSchoolCode());
         courseResponseDTO.setSchoolName(school.getSchoolName());
         //get assign subjects and add to course payload
         List<SubjectAssignToCourse> subjectList = subjectAssignRepo.findAllByCourse(course);
         List<SubjectAssignResponseDTO> subList = new ArrayList<>();
         for (SubjectAssignToCourse s : subjectList){
             SubjectAssignResponseDTO sub = new SubjectAssignResponseDTO(
                     s.getAssignId(),
                     s.getSubjects().getSubjectId(),
                     s.getSubjects().getSubjectCode(),
                     s.getSubjects().getName()
             );
             subList.add(sub);
         }
         log.info("Success mapping subject to course");
         courseResponseDTO.setSubjectlist(subList);
         return courseResponseDTO;
     }catch (Exception e){
         log.error("Error fetching course details {} ",e.getMessage());
         throw new HandleException("Something went wrong during fetching course details");
     }
    }

    @Override
    public String uploadCourse(CourseEditDTO courseEditDTO, long courseId) {
        final Course c = EntityUtils.getEntityDetails(courseId,courseRepo,"Course");
        try {
            final Course convetedStuent = courseMapper.courseEditDTOtoCourse(courseEditDTO);
            courseRepo.save(convetedStuent);
            return "Course " + courseId + " Successfully updated";
        }catch (Exception e){
            throw new HandleException("Something went wrong during upload Course details");
        }

    }

    @Override
    public String deleteStudent(long courseId) {
        final Course c = EntityUtils.getEntityDetails(courseId,courseRepo,"Course");
        try {
            c.setActive_statue(false);
            courseRepo.save(c);
            return "Course " + courseId + " Successfully Deleted";
        }catch (Exception e){
            throw new HandleException("Something went wrong during deleting course details");
        }
    }

    @Override
    public Page<CourseResponseDTO> getAllCourse(Pageable pageable) {
        try{
            Page<Course> courses = courseRepo.findAll(pageable);
            return courses.map(courseMapper::courseToCourseResponseDTO);
            //we can use other way below
            // return courses.map(course -> courseMapper.courseToCourseResponseDTO(course));
        }catch (Exception e){
            throw new HandleException("Something went wrong during fetching course details");
        }

    }
}
