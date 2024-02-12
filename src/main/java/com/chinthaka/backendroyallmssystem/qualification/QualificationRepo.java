package com.chinthaka.backendroyallmssystem.qualification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepo extends JpaRepository<Qualification,Long> {
}
