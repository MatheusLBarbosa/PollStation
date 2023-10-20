package com.mlb.pollstation.controllers.advice;

import com.mlb.pollstation.dto.response.GenericResponse;
import com.mlb.pollstation.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllersAdvices {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<GenericResponse> generalException(GeneralException ex) {
        return new ResponseEntity<GenericResponse>(new GenericResponse(ex.getMessage(), ex.getStatusCode()),
                ex.getStatus());
    }
}
