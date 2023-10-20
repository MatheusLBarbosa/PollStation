package com.mlb.pollstation.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    private int statusCode;

    public GeneralException(String message) {
        super(String.format(message));
    }
    public GeneralException(String message, HttpStatus status) {
        super(String.format(message));
        this.statusCode = status.value();
        this.status = status;
    }
}
