package com.chinthaka.backendroyallmssystem.batch;


import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBatchService {

    BatchResponseDTO getById(long batchId);

    String deleteBatch(long batchId);

    String addBatch(BatchDTO batchDTO);

    Page<BatchResponseDTO> getAllBatch(Pageable pageable, long courseId);

    Page<BatchResponseDTO> getAllBatchData(Pageable pageable);
}
