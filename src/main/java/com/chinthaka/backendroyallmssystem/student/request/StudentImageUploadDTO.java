package com.chinthaka.backendroyallmssystem.student.request;

import lombok.Data;

@Data
public class StudentImageUploadDTO {
    private long studentId;
    public String imageUrl;
}
