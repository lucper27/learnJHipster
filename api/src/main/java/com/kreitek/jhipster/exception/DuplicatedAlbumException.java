package com.kreitek.jhipster.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;


public class DuplicatedAlbumException extends RuntimeException {

    private final StatusType STATUS = Status.CONFLICT;
    public DuplicatedAlbumException(String message) {
        super(message);
    }

    public StatusType getStatus() {
        return this.STATUS;
    }


}
