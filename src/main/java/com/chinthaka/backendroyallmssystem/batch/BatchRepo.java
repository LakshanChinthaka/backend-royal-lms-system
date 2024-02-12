package com.chinthaka.backendroyallmssystem.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepo extends JpaRepository<Batch,Long> {

    Batch findByCode(String code);
}
