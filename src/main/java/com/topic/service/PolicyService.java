package com.topic.service;

import com.topic.entity.ActionType;
import com.topic.entity.ResourceType;
import com.topic.service.dto.Permission;

public interface PolicyService {

    Permission getPermission(Long userId, Long entityId, ActionType actionType, ResourceType resourceType);

}
