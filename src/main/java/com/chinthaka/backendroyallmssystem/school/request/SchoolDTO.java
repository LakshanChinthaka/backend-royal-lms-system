package com.chinthaka.backendroyallmssystem.school.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolDTO {

    private Long schoolID;

    private String schoolName;

    private String schoolCode;


}
