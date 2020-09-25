package com.hackerearth.connectfourgameapi.restcontroller;

import com.hackerearth.connectfourgameapi.models.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiErrorController {
    Logger log = LoggerFactory.getLogger(ApiErrorController.class);

    //used to throw exceptions from anywhere
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorObject> handleResponseStatusException(ResponseStatusException ex) {
        ErrorObject errorObject = new ErrorObject(ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.accepted().body(errorObject);
    }
    //catch any exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleEntityNotFound(Exception ex) {
        log.error(ex.getMessage());
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity(errorObject, HttpStatus.NOT_FOUND);
    }
}
