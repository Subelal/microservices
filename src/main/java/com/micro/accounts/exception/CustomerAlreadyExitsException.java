package com.micro.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExitsException extends RuntimeException{

    public CustomerAlreadyExitsException(String message){
        super(message);
    }
}
