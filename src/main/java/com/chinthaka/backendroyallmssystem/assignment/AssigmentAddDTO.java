package com.chinthaka.backendroyallmssystem.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssigmentAddDTO {
    private String assiCode;
    private long batchId;
    private String assiUrl;
    private String deadLine;
}
