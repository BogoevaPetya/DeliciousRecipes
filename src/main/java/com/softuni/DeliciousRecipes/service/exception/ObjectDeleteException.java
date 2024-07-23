package com.softuni.DeliciousRecipes.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class ObjectDeleteException extends RuntimeException{
    public ObjectDeleteException() {
    }
}
