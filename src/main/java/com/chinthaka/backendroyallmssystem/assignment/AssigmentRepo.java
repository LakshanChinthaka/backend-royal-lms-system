package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.batch.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssigmentRepo extends JpaRepository<Assigment, Long> {

    boolean existsByAssiCode(String assiCode);

    Page<Assigment> findAllByBatchId(Long batchId, Pageable pageable);


    @Query(value = "SELECT a.created_date " +
            "FROM assigment a " +
            "WHERE assi_id = :assiId",
            nativeQuery = true)
    LocalDateTime findDate(long assiId);

}
