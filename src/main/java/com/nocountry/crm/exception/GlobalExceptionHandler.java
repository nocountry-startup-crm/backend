package com.nocountry.crm.exception;

import com.nocountry.crm.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FunctionalException.class)
    public ResponseDto<String> handleFunctionalException(FunctionalException ex) {
        return new ResponseDto<>(null, ex.getErrorCode(), 1, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<String> handleTechnicalException(Exception ex) {
        return new ResponseDto<>(null, HttpStatus.INTERNAL_SERVER_ERROR, 2, ex.getMessage());
    }
}
