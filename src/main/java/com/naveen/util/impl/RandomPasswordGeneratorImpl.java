package com.naveen.util.impl;

import com.naveen.util.RandomPasswordGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomPasswordGeneratorImpl implements RandomPasswordGenerator {
    @Override
    public String generateRandomPassword(int passwordLength) {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").substring(0, passwordLength);
    }
}
