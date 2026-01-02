package com.topic.service.impl;

import com.topic.service.ThreadService;
import com.topic.service.dto.CreateThreadDto;
import com.topic.service.dto.EntityId;
import org.springframework.stereotype.Service;

@Service
public class ThreadServiceImpl implements ThreadService {


    @Override
    public Long createThread(CreateThreadDto data) {
        // TODO
        return 0L;
    }

    @Override
    public void getThreadMainInfo(EntityId threadId) {
        // TODO
    }

    @Override
    public void getThreadFullInfo(EntityId threadId) {
        // TODO
    }
}
