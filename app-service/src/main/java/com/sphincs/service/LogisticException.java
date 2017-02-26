package com.sphincs.service;

import org.springframework.http.HttpStatus;

public class LogisticException extends RuntimeException {

    private HttpStatus status;

    public LogisticException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
