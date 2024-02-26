package com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmitRepo extends JpaRepository<AssigmentSubmit,Long> {

    boolean existsByAssiCodeAndStudentId(String assiCode, long studentId);

    AssigmentSubmit findByAssiCodeAndStudentId(String assiCode, long studentId);


    @Query(value = "SELECT a.grade FROM assigment_sumbit a WHERE code = :assiCode",nativeQuery = true)
    String findByCode(String assiCode);
}
