package com.chinthaka.backendroyallmssystem.course;

import com.chinthaka.backendroyallmssystem.course.request.CourseDTO;
import com.chinthaka.backendroyallmssystem.course.request.CourseEditDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseDTO;
import com.chinthaka.backendroyallmssystem.course.response.CourseResponseForDrop;
import com.chinthaka.backendroyallmssystem.excaption.AlreadyExistException;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.school.School;
import com.chinthaka.backendroyallmssystem.school.SchoolRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignToCourse;
import com.chinthaka.backendroyallmssystem.subjectAssign.response.SubjectAssignResponseDTO;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourserServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final SchoolRepo schoolRepo;
    private final SubjectAssignRepo subjectAssignRepo;
    private final Counter status200Counter;
    private final Counter status400Counter;;
    private final Counter status500Counter;



    @Override
    public String createCourse(CourseDTO courseDTO, String imageUrl) {
        log.info("Create new course:{}", courseDTO);
        if (courseDTO == null) {
            status400Counter.increment();;
            throw new AlreadyExistException("Course details not provide");
        }
        if (courseRepo.existsByCode(courseDTO.getCode())) {
            status400Counter.increment();;
            throw new AlreadyExistException("Course code: " + courseDTO.getCode() + " Already exist");
        }
        try {
            Course course = courseMapper.courseSaveDTOtoCourse(courseDTO);
            School school = EntityUtils.getEntityDetails(courseDTO.getSchoolId(), schoolRepo, "School");
            course.setSchool(school);
            course.setImageUrl(imageUrl);
            courseRepo.save(course);
            status200Counter.increment();;
            return "Course successfully created";
        } catch (Exception e) {
            log.error("Error while creating new course: {}", e.getMessage());
            status500Counter.increment();;
            throw new HandleException("Something went wrong during creating course");
        }
    }

    @Override
    public CourseResponseDTO courseGetById(long courseId) {
        log.info("Start get course details by course id: {} ", courseId);
        final Course course = EntityUtils.getEntityDetails(courseId, courseRepo, "Course");
        School school = EntityUtils.getEntityDetails(
                course.getSchool().getSchoolID(), schoolRepo, "School");
        try {
            CourseResponseDTO courseResponseDTO = courseMapper.courseToCourseResponseDTO(course);
            courseResponseDTO.setCreateBy(course.getCreateBy());
            courseResponseDTO.setCreatedDate(course.getCreatedDate());
            courseResponseDTO.setModifiedBy(course.getModifiedBy());
            courseResponseDTO.setModifiedData(course.getModifiedData());
            courseResponseDTO.setSchoolCode(school.getSchoolCode());
            courseResponseDTO.setSchoolName(school.getSchoolName());
            //get assign subjects and add to course payload
            List<SubjectAssignToCourse> subjectList = subjectAssignRepo.findAllByCourse(course);
            List<SubjectAssignResponseDTO> subList = new ArrayList<>();
            for (SubjectAssignToCourse s : subjectList) {
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
            status200Counter.increment();;
            return courseResponseDTO;
        } catch (Exception e) {
            log.error("Error fetching course details : {} ", e.getMessage());
            status500Counter.increment();;
            throw new HandleException("Something went wrong during fetching course details");
        }
    }

    //TODO: need to fix course update issue
    @Override
    public String uploadCourse(CourseEditDTO courseEditDTO, long courseId) {
        log.info("Update course id:{} details", courseId);
        if (courseEditDTO == null) {
            status400Counter.increment();;
            throw new NotFoundException("Course details not provide");
        }
        final Course course = EntityUtils.getEntityDetails(courseId, courseRepo, "Course");
        School  school = EntityUtils.getEntityDetails(courseEditDTO.getSchoolId(), schoolRepo, "School");
        try {
//            final Course updatedCourse = courseMapper.courseEditDTOtoCourse(courseEditDTO);
            course.setName(courseEditDTO.getName());
            course.setCategory(courseEditDTO.getCategory());
            course.setDescription(courseEditDTO.getDescription());
            course.setTotalCredit(courseEditDTO.getTotalCredit());
            course.setTotalHours(courseEditDTO.getTotalHours());
            course.setCourseType(courseEditDTO.getCourseType());
            course.setFees(courseEditDTO.getFees());
            course.setDuration(courseEditDTO.getDuration());
            course.setMedium(courseEditDTO.getMedium());
            course.setSchool(school);

            courseRepo.save(course);
            status200Counter.increment();;
            return "Course " + courseId + " Successfully updated";
        } catch (Exception e) {
            log.error("Error while updating course Details : {}", e.getMessage());
            status500Counter.increment();;
            throw new HandleException("Something went wrong during upload Course details");
        }

    }

    @Override
    public String deleteStudent(long courseId) {
        final Course course = EntityUtils.getEntityDetails(courseId, courseRepo, "Course");
        try {
            courseRepo.deleteById(course.getCourseId());
            status200Counter.increment();;
            return "Course " + courseId + " Successfully Deleted";
        } catch (Exception e) {
            status500Counter.increment();;
            throw new HandleException("Something went wrong during deleting course details");
        }
    }

    @Override
    public Page<CourseResponseDTO> getAllCourse(Pageable pageable) {
        try {
            Page<Course> courses = courseRepo.findAll(pageable);
            //above did that same process but approach is different;
            return courses.map(course -> {
                CourseResponseDTO courseResponseDTO = courseMapper.courseToCourseResponseDTO(course);
                // next Fetch school details
                School school = EntityUtils.getEntityDetails(course.getSchool().getSchoolID(), schoolRepo, "School");
                courseResponseDTO.setSchoolCode(school.getSchoolCode());
                courseResponseDTO.setSchoolName(school.getSchoolName());
                courseResponseDTO.setCreateBy(course.getCreateBy());
                courseResponseDTO.setCreatedDate(course.getCreatedDate());
                courseResponseDTO.setModifiedBy(course.getModifiedBy());
                courseResponseDTO.setModifiedData(course.getModifiedData());
                courseResponseDTO.setImageUrl(course.getImageUrl());
                // Fetch subject assignments and map to response DTO
                List<SubjectAssignToCourse> subjectList = subjectAssignRepo.findAllByCourse(course);
                List<SubjectAssignResponseDTO> subList = subjectList.stream()
                        .map(s -> new SubjectAssignResponseDTO(
                                s.getAssignId(),
                                s.getSubjects().getSubjectId(),
                                s.getSubjects().getSubjectCode(),
                                s.getSubjects().getName()))
                        .collect(Collectors.toList());
                courseResponseDTO.setSubjectlist(subList);
                status200Counter.increment();;
                return courseResponseDTO;
            });
        } catch (Exception e) {
            status500Counter.increment();;
            throw new HandleException("Something went wrong during fetching course details");
        }

    }

    @Override
    public List<CourseResponseForDrop> getAll() {
        List<Course> courses = courseRepo.findAll();
        List<CourseResponseForDrop> courseResponseForDrops = new ArrayList<>();

        for (Course c : courses) {
            CourseResponseForDrop courseResponseForDrop = new CourseResponseForDrop();
            // Assuming you have methods in Course and CourseResponseForDrop classes to retrieve necessary data
            courseResponseForDrop.setCourseId(c.getCourseId());
            courseResponseForDrop.setCode(c.getCode());
            courseResponseForDrop.setName(c.getName());
            // Set other properties as needed

            courseResponseForDrops.add(courseResponseForDrop);
        }
        status200Counter.increment();;
        return courseResponseForDrops;
    }
}
