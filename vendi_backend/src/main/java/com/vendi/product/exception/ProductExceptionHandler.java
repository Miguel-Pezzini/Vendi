package com.vendi.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(InvalidMainPhotoException.class)
    public ResponseEntity<String> handleInvalidMainPhotoException(InvalidMainPhotoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(MaxPhotoLimitExceededException.class)
    public ResponseEntity<String> handleMaxPhotoLimitExceededException(MaxPhotoLimitExceededException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
