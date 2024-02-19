package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.course.Course;
import com.chinthaka.backendroyallmssystem.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectAssignRepo extends JpaRepository<SubjectAssignToCourse,Long> {

    List<SubjectAssignToCourse> findAllByCourse(Course course);

    SubjectAssignToCourse findByCourseAndSubjects(Course course, Subject subjects);

    void deleteAllBySubjects(Subject subject);

    boolean existsBySubjects(Subject subject);

    boolean existsAllByCourseAndSubjects(Course course, Subject subjects);

    List<SubjectAssignToCourse> findAllBySubjects(Subject subject);


//    boolean existsAllByCourseAndSubjects(Course course, Subject subjects);
}
