package com.example.telephonebook.exeption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
        log.info("Error = {}", message);
    }
}
