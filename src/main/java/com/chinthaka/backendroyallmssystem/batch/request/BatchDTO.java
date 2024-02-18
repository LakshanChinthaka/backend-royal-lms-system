package com.chinthaka.backendroyallmssystem.batch.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {
    private Long batchId;
    private String code;
        private long courseId;
    private long schoolId;

}
