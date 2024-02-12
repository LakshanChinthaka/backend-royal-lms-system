package com.chinthaka.backendroyallmssystem.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepo extends JpaRepository<School,Long> {

    School findBySchoolCode(String schoolCode);

    List<School> findAllByOrderByCreatedDateDesc();
}

