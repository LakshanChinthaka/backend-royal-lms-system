package com.chinthaka.backendroyallmssystem.batch;

import com.chinthaka.backendroyallmssystem.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepo extends JpaRepository<Batch,Long> {

    Batch findByCode(String code);


    Page<Batch> findAllByCourse(Pageable pageable, Course course);
}
