package com.chinthaka.backendroyallmssystem.student;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    boolean existsByNic(String nic);

    Student findByNic(String nic);

    @Query(value = "SELECT s.profile_url FROM student s WHERE s.st_id=:studentId", nativeQuery = true)
    String findProfileUrlById(long studentId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Student s SET s.imageUrl = :imageUrl WHERE s.id = :studentId")
    void uploadProfileUrl(String imageUrl, long studentId);


//    @Query("SELECT COUNT(s), s.createdDate FROM Student s WHERE  GROUP BY s.createdDate")
//    List<Object[]> countStudentsByMonth(int month, int year);
}
