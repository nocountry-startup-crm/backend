package com.nocountry.crm.exception;

import org.springframework.http.HttpStatusCode;

public class FunctionalException extends RuntimeException {
    private HttpStatusCode errorCode;

    public FunctionalException(String message, HttpStatusCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }
}
