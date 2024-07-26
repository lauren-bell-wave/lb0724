package com.interview.lb0724;

import com.interview.lb0724.exceptions.OutsidePermitedRangeException;
import com.interview.lb0724.exceptions.RentalServiceDoesNotExistException;
import com.interview.lb0724.exceptions.ToolDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ToolDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleToolDoesNotExistException(ToolDoesNotExistException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RentalServiceDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRentalServiceDoesNotExistException(RentalServiceDoesNotExistException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OutsidePermitedRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleOutsidePermitedRangeException(OutsidePermitedRangeException e) {
        return e.getMessage();
    }

    @ExceptionHandler({DateTimeParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadDateParseException(DateTimeParseException e) {
        return "Date provided is not a valid date";
    }
}
