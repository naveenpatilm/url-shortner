package com.naveen.util;

import com.naveen.util.impl.RandomStringGeneratorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RandomStringGeneratorTest {
    RandomStringGenerator randomStringGenerator = new RandomStringGeneratorImpl();

    @Test
    public void generateRandomString_emptyString_zeroStringLength() {
        String randomString = randomStringGenerator.generateRandomString(0);
        assertEquals(0, randomString.length());
    }

    @Test
    public void generateRandomString_emptyString_negativeStringLength() {
        String randomString = randomStringGenerator.generateRandomString(-1);
        assertEquals(0, randomString.length());
    }

    @Test
    public void generateRandomString_validRandomStringWithLength7_StringLength7() {
        String randomString = randomStringGenerator.generateRandomString(7);
        assertEquals(7, randomString.length());
    }

    @Test
    public void generateRandomString_uniqueGeneratedString_generateMultipleString() {
        String randomString1 = randomStringGenerator.generateRandomString(7);
        String randomString2 = randomStringGenerator.generateRandomString(7);
        assertNotEquals(randomString1, randomString2);
        assertEquals(randomString1.length(), randomString2.length());
    }

}
