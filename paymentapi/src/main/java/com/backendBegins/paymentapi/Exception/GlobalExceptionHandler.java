package com.backendBegins.paymentapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePaymentNotFound(PaymentNotFoundException ex) {
        return ex.getMessage();
    }

}