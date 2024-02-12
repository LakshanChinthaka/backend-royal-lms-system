package com.chinthaka.backendroyallmssystem.batch;

import com.chinthaka.backendroyallmssystem.batch.response.BatchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BatchMapper {

    BatchMapper batchMapper = Mappers.getMapper(BatchMapper.class );

    BatchResponseDTO batchToBatchResponseDTO(Batch batch);
}
