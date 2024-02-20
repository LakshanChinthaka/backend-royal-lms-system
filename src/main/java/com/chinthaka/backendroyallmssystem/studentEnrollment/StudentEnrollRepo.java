package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEnrollRepo extends JpaRepository<StudentEnroll,Long> {

    boolean existsByStudent(Student student);

    StudentEnroll findByStudent(Student student);

    void deleteByStudent(long studentId);

    Page<StudentEnroll> findAllByBatch(Batch batch,Pageable pageable);

    //Test
    @Query("SELECT COUNT(e), e.batch FROM StudentEnroll e WHERE e.batch = :batch GROUP BY e.batch")
     Integer count(Batch batch);
}
