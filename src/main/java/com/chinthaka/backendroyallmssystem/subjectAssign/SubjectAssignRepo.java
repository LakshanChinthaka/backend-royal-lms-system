package com.chinthaka.backendroyallmssystem.subjectAssign;

import com.chinthaka.backendroyallmssystem.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectAssignRepo extends JpaRepository<SubjectAssignToCourse,Long> {

    List<SubjectAssignToCourse> findAllByCourse(Course course);
}
