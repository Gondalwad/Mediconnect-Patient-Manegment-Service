package com.PatientService.Service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.net.ResponseCache;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err-> errors.put(err.getField(),err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // I Designed this because I got an error of Duplicate Key value from Database leading the following exception trigger
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailExistsException ex){

        log.warn("Email Already Exists{}", ex.getMessage());
        Map<String,String> error = new HashMap<>();
        error.put("message",ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }


    // Handles Exception if patient not found while searching with id
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex){
        Map<String,String> error = new HashMap<>();

        error.put("message","Patient Not Found");

        return ResponseEntity.badRequest().body(error);
    }
}
