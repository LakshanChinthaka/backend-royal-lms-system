package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.school.SchoolRepo;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourserServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final SchoolRepo schoolRepo;

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
        final Course course = EntityUtils.getEntityDetails(courseId,courseRepo,"Course");
        School school = EntityUtils.getEntityDetails(
                course.getSchool().getSchoolID(),schoolRepo,"School");
        CourseResponseDTO courseResponseDTO = courseMapper.courseToCourseResponseDTO(course);
        courseResponseDTO.setSchoolCode(school.getSchoolCode());
        courseResponseDTO.setSchoolName(school.getSchoolName());
        return courseResponseDTO;
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
