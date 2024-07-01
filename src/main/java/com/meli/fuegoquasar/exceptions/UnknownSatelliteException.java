package com.meli.fuegoquasar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnknownSatelliteException extends RuntimeException {
    public UnknownSatelliteException(String msg) {
        super(msg);
    }
}
