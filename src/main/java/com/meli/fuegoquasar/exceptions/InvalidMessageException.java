package com.meli.fuegoquasar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidMessageException extends RuntimeException{
    public InvalidMessageException(String msg) {
        super(msg);
    }
}
