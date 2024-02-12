package com.chinthaka.backendroyallmssystem.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    boolean findByCode(Long courseId);
}
