package se.omegapoint.academy.opmarketplace.customer.domain;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public final class User {
    private final int FIRSTNAME_LENGTH = 25;
    private final int LASTNAME_LENGTH = 25;

    private final String FIRSTNAME_ILLEGAL_FORMAT = "First eventType cannot be longer than " + FIRSTNAME_LENGTH + "characters and can only contain letters and '-'";
    private final String LASTNAME_ILLEGAL_FORMAT = "Last eventType cannot be longer than " + LASTNAME_LENGTH + "characters and can only contain letters and '-'";

    private final String firstName;
    private final String lastName;

    public User(String firstName, String lastName) {
        notBlank(firstName);
        notBlank(lastName);
        firstName = firstName.trim();
        lastName = lastName.trim();
        isTrue(firstName.length() <= FIRSTNAME_LENGTH && firstName.matches("[\\w\\-]+"), FIRSTNAME_ILLEGAL_FORMAT);
        isTrue(lastName.length() <= LASTNAME_LENGTH && lastName.matches("[\\w\\-]+"), LASTNAME_ILLEGAL_FORMAT);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User changeFirstName(String firstName){
        return new User(firstName, lastName());
    }

    public User changeLastName(String lastName){
        return new User(firstName(), lastName);
    }

    public String firstName(){
        return firstName;
    }

    public String lastName(){
        return lastName;
    }

    public String fullName(){
        return firstName() + " " + lastName();
    }
}
