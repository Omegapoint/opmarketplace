package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import org.apache.commons.lang.RandomStringUtils;

import java.util.UUID;

public class StringRandomizer {
    private static final int STRING_LENGTH = 30;

    public static String randomString() {
        return RandomStringUtils.random(STRING_LENGTH);
    }
}
