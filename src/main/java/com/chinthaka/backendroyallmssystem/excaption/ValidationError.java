package com.chinthaka.backendroyallmssystem.excaption;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationError {

    private Integer code;

    private String message;

    private Map<String,String > validationErrors;

    public ValidationError(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
