package com.chinthaka.backendroyallmssystem.excaption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HandleException extends RuntimeException{

    public HandleException(String message) {
        super(message);
    }
}
