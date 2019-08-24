package com.example.petservice.api;

import com.example.petservice.api.model.ApiErrorDto;
import com.example.petservice.service.exception.NameConflictException;
import com.example.petservice.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiErrorDto> handle(ResourceNotFoundException e, WebRequest req) {
        final ApiErrorDto body = new ApiErrorDto()
            .type("petsvc/not-found").title("Resource not found").detail(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({NameConflictException.class})
    public ResponseEntity<ApiErrorDto> handle(NameConflictException e, WebRequest req) {
        final ApiErrorDto body = new ApiErrorDto()
            .type("petsvc/name-conflict").title("Name already taken").detail(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}