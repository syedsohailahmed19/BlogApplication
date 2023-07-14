package com.blogApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldValue;

    private Long id;

    public ResourceNotFoundException(String resourceName, String fieldValue, Long id) {

        super(String.format("%s not found %S:'%s'", resourceName, fieldValue, id) );
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Long getId() {
        return id;
    }
}
