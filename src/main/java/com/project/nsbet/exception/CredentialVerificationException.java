package com.project.nsbet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredentialVerificationException extends Exception {

    public CredentialVerificationException(String message) {
        super(message);
    }

}
