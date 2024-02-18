package com.chinthaka.backendroyallmssystem.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    boolean existsByNic(String nic);

    Student findByNic(String nic);
}
