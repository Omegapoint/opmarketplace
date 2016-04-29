package se.omegapoint.academy.opmarketplace.customer.infrastructure.data_extraction;

import org.apache.commons.lang.RandomStringUtils;

public class ImportantUsers {
    private final int NO_USERS = 1000;

    public final String[] keys;
    public final String[][] values;

    public ImportantUsers() {
        keys = new String[]{"email"};
        values = new String[NO_USERS][1];
        for (int i = 0; i < values.length; i++) {
            values[i][0] = RandomStringUtils.randomAlphabetic(20) + "@email.com";
        }
    }
}
