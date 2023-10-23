package com.kreitek.jhipster.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ErrorDescription implements Serializable {
    private HttpStatus status;
    private String Detail;
    private String Message;

    public ErrorDescription(String detail, String message) {
        this.Detail = detail;
        this.Message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
