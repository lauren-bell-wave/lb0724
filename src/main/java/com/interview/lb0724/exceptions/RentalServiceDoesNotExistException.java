package com.interview.lb0724.exceptions;

public class RentalServiceDoesNotExistException extends RuntimeException {
    public RentalServiceDoesNotExistException(String message) {
       super(message);
    }
}
