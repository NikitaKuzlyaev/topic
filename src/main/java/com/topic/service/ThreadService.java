package com.topic.service;

import com.topic.service.dto.CreateThreadDto;
import com.topic.service.dto.EntityId;

public interface ThreadService {

    Long createThread(CreateThreadDto data);

    void getThreadMainInfo(EntityId threadId); // TODO: it's not `void` actually

    void getThreadFullInfo(EntityId threadId); // TODO: it's not `void` actually

}