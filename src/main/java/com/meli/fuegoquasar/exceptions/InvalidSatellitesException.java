package com.meli.fuegoquasar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidSatellitesException extends RuntimeException {
    public InvalidSatellitesException(String msg) {
        super(msg);
    }
}
