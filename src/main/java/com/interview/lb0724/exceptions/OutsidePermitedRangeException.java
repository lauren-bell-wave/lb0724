package com.interview.lb0724.exceptions;

public class OutsidePermitedRangeException extends RuntimeException {
    public OutsidePermitedRangeException(String message) {
        super(message);
    }
}
