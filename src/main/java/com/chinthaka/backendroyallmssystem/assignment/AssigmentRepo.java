package com.chinthaka.backendroyallmssystem.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigmentRepo extends JpaRepository<Assigment,Long> {

    boolean existsByAssiCode(String assiCode);
}
