package com.project.nsbet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredentialVerificationException extends Exception {
    public CredentialVerificationException() {
    }

    public CredentialVerificationException(String message) {
        super(message);
    }

    public CredentialVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialVerificationException(Throwable cause) {
        super(cause);
    }

    public CredentialVerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
