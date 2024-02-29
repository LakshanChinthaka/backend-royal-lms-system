package com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmitRepo extends JpaRepository<AssigmentSubmit,Long> {

    boolean existsByAssiCodeAndStudentId(String assiCode, long studentId);

    AssigmentSubmit findByAssiCodeAndStudentId(String assiCode, long studentId);


    @Query(value = "SELECT a.grade " +
            "FROM assigment_sumbit a " +
            "WHERE code = :assiCode",nativeQuery = true)
    String findByCode(String assiCode);

    @Query(value = "SELECT grade, COUNT(*) " +
            "FROM assigment_sumbit " +
            "GROUP BY grade", nativeQuery = true)
    List<Object[]> countByGrade();

    @Query(value = "SELECT COUNT(*) " +
            "FROM assigment_sumbit a " +
            "WHERE grade = 'Pending' OR grade = 'Repeat'", nativeQuery = true)
    long countFailAss();

//    @Query(value = "SELECT COUNT(*) " +
//            "FROM assigment_sumbit a " +
//            "WHERE grade = 'Pending' " +
//            "OR grade = 'Repeat' " +
//            "OR  grade = 'Merit' " +
//            "OR grade = 'Distinction'" +
//            "GROUP BY grade", nativeQuery = true)
//    List<Object[]> countByddGrade();
}
