package com.chinthaka.backendroyallmssystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardResponse {

    private Integer code;

    private String message;

    private Object data;
}
