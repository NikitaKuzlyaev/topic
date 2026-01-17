package com.topic.service.dto;

import com.topic.entity.ActionType;
import com.topic.entity.ResourceType;
import lombok.Data;

import java.util.function.Supplier;

@Data
public class Permission {

    private final boolean isAccessOpen;

    private final Long userId;
    private final Long entityId;
    private final ActionType actionType;
    private final ResourceType resourceType;

    public <X extends RuntimeException> Permission failIfDenied(Supplier<X> exceptionSupplier){
        if (!this.isAccessOpen){
            throw exceptionSupplier.get();
        }
        return this;
    }
}
