package com.chinthaka.backendroyallmssystem.studentEnrollment;

import com.chinthaka.backendroyallmssystem.student.Student;
import com.chinthaka.backendroyallmssystem.subjectAssign.SubjectAssignRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEnrollRepo extends JpaRepository<StudentEnroll,Long> {

    boolean existsByStudent(Student student);

    StudentEnroll findByStudent(Student student);

}
