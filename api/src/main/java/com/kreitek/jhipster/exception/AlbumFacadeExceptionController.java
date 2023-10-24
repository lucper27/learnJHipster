package com.kreitek.jhipster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlbumFacadeExceptionController extends ResponseEntityExceptionHandler {

@ExceptionHandler
public ResponseEntity<ErrorDescription> handlePersonalizedException(AlbumFacadeException ex) {

    ErrorDescription errorDescription = new ErrorDescription(ex.getMessage(), "Some Other info");

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errorDescription);
}
}


