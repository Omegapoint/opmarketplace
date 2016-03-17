package se.omegapoint.academy.opmarketplace.customer.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class UserTest {
    @Test
    @Parameters
    public void validInput(String firstName, String lastName) throws Exception {
            User user = new User(firstName, lastName);
            assertEquals(firstName, user.firstName());
            assertEquals(lastName, user.lastName());
    }

    public String[][] parametersForValidInput() {
        return new String[][]{{"val", "id"}, {"va-l", "id"}, {"val", "i-d"}, {"va-l", "i-d"}, {"va--l", "i--d"}};
    }
    @Test(expected = IllegalArgumentValidationException.class)
    @Parameters
    public void invalidInput(String firstName, String lastName) throws Exception {
        new User(firstName, lastName);
    }

    public String[][] parametersForInvalidInput() throws Exception{
        return new String[][]{{"iiiiiiiiiiiiiiiiiiiiiiiiii", "i"}, {"i", "iiiiiiiiiiiiiiiiiiiiiiiiii"}, {"&^", "valid"}, {"valid", "&^"}, {"_", "valid"}, {"valid", "_"}};
    }
}