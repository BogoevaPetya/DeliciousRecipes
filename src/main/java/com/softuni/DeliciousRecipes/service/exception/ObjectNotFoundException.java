package com.softuni.DeliciousRecipes.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    private final Object id;

    public ObjectNotFoundException(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
