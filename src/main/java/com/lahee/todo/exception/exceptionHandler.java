package com.lahee.todo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> ex(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
