package com.nocountry.crm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Getter
@Setter
public class ResponseBody<T> {
    private T data;
    private String message = "";
    private int status;
    private long timestamp;

    public ResponseBody(T data, int status) {
        this.data = data;
        this.status = status;
        this.timestamp = generateTimestamp();
    }

    public ResponseBody(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.timestamp = generateTimestamp();
    }

    private long generateTimestamp() {
        long currentMillis = currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date resultdate = new Date(currentMillis);
        String timestampStr = sdf.format(resultdate);

        return Long.parseLong(timestampStr);
    }
}
