package com.chinthaka.backendroyallmssystem.employee.request;

import lombok.Data;

@Data
public class ImageUploadDTO {
    private long employeeId;
    private String imageUrl;
}
