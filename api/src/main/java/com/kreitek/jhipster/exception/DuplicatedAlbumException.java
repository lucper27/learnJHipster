package com.kreitek.jhipster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedAlbumException extends RuntimeException {

    public DuplicatedAlbumException(String message) {
        super(message);
    }



}
