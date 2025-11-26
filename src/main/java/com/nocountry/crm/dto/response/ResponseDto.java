package com.nocountry.crm.dto.response;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseDto<T> extends ResponseEntity<ResponseBody<T>> {
    public ResponseDto(T data, HttpStatusCode httpStatus, int status) {
        super(new ResponseBody<T>(data, status), httpStatus);
    }

    public ResponseDto(T data, HttpStatusCode httpStatus, int status, String message) {
        super(new ResponseBody<T>(data, status, message), httpStatus);
    }
}
