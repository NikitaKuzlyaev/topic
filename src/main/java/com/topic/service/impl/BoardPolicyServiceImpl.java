package com.topic.service.impl;

import com.topic.entity.ActionType;
import com.topic.entity.ResourceType;
import com.topic.service.BoardPolicyService;
import com.topic.service.dto.Permission;
import org.springframework.stereotype.Service;

@Service
public class BoardPolicyServiceImpl implements BoardPolicyService {

    @Override
    public Permission getPermission(Long userId, Long entityId, ActionType actionType, ResourceType resourceType) {
        return new Permission(true, userId, entityId, actionType, resourceType);
        //return new Permission(false, userId, entityId, actionType, resourceType);
    }
}
