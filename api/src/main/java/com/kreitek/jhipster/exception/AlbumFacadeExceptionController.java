package com.kreitek.jhipster.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlbumFacadeExceptionController extends ResponseEntityExceptionHandler {

@ExceptionHandler
public ResponseEntity<ErrorDescription> handlePersonalizedException(DuplicatedAlbumException ex) {
    ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
    ErrorDescription errorDescription = new ErrorDescription(responseStatus.value(), ex.getMessage(), "Some Other info");

    return ResponseEntity
        .status(responseStatus.value())
        .body(errorDescription);
}
}


