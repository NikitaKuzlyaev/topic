package com.topic.service.impl;

import com.topic.service.SaltGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SimpleSaltGenerator implements SaltGenerator {

    // прибито гвозями. да вроде и норм
    private final SecureRandom random = new SecureRandom();

    @Override
    public byte[] generate() {
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }
}
