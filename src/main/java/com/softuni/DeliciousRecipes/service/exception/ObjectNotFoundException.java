package com.softuni.DeliciousRecipes.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    private final Long id;

    public ObjectNotFoundException(Long id) {
        this.id = id;
    }
}
