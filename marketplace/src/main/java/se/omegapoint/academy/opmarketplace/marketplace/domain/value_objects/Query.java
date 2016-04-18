package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import se.omegapoint.academy.opmarketplace.marketplace.MainConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class Query {

    protected final static int MAX_LENGTH = 100;
    protected final static int SEARCH_TERMS = 5;

    protected final static String ILLEGAL_LENGTH = "Illegal Format: Description cannot be longer than " + MAX_LENGTH + " characters.";
    protected final static String ILLEGAL_CHARACTERS = "Illegal Format: Description can only contain letters, digits and '.,%&@/'";
    protected final static String ILLEGAL_AMOUNT_OF_TERMS = "Illegal Format: Query can only contain " + SEARCH_TERMS + " search terms.";

    private final List<String> terms;

    public Query(String query) {
        String trimmedQuery = notBlank(query).trim();
        if(MainConfiguration.VALIDATION){
            isTrue(trimmedQuery.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
            isTrue(trimmedQuery.matches("[\\w\\.,?%&@/ ]+"), ILLEGAL_CHARACTERS);
            isTrue(trimmedQuery.split(" ").length <= SEARCH_TERMS, ILLEGAL_AMOUNT_OF_TERMS);
        }
        terms = Arrays.asList(trimmedQuery.split(" ")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<String> terms(){
        return this.terms;
    }
}
