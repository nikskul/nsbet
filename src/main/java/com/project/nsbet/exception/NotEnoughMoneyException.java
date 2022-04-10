package com.project.nsbet.exception;

public class NotEnoughMoneyException extends BadRequestException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
