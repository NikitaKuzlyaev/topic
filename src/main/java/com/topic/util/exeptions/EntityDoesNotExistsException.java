package com.topic.util.exeptions;

public class EntityDoesNotExistsException extends RuntimeException {
    public EntityDoesNotExistsException(String message) {
        super(message);
    }
}
