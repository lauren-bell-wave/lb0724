package com.interview.lb0724.exceptions;

public class ToolDoesNotExistException extends RuntimeException{

    public ToolDoesNotExistException(String message) {
        super(message);
    }
}
