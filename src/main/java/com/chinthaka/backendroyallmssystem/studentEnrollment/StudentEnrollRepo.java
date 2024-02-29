package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollRepo extends JpaRepository<StudentEnroll,Long> {

    boolean existsByStudent(Student student);

    StudentEnroll findByStudent(Student student);

    void deleteByStudent(long studentId);

    Page<StudentEnroll> findAllByBatch(Batch batch,Pageable pageable);

    //Test
    @Query("SELECT COUNT(e), e.batch FROM StudentEnroll e WHERE e.batch = :batch GROUP BY e.batch")
     Integer count(Batch batch);

    //1
    @Query(value = "SELECT course_id ,COUNT(*) " +
            "FROM student_enroll " +
            "GROUP BY course_id " +
            "ORDER BY COUNT(*) " +
            "DESC ", nativeQuery = true)
    List<Long[]> topCourse();

    //2
    @Query(value = "SELECT course_id,COUNT(*) FROM  student_enroll WHERE  course_id = :aLong ", nativeQuery = true)
    Integer countCourse(Long aLong);
}
