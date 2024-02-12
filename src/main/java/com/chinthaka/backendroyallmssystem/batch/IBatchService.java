package com.chinthaka.backendroyallmssystem.batch;


import com.chinthaka.backendroyallmssystem.batch.request.BatchDTO;
import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;

public interface IBatchService {

    BatchResponseDTO getById(long batchId);

    String deleteBatch(long batchId);

    String addBatch(BatchDTO batchDTO);
}
