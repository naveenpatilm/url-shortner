package com.naveen.util.impl;

import com.naveen.util.RandomStringGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomStringGeneratorImpl implements RandomStringGenerator {
    @Override
    public String generateRandomString(int stringLength) {
        if (stringLength <= 0) return "";
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").substring(0, stringLength);
    }
}
