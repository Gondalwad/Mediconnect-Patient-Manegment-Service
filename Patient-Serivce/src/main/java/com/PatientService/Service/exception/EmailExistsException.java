package com.PatientService.Service.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
