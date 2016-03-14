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
    public void valid(String firstName, String lastName){
        try {
            assertEquals(firstName + " " + lastName, new User(firstName, lastName).fullName());
        }catch (Exception e){
            fail(firstName + " " + lastName+": " + e.getMessage());
        }
    }

    @Test
    public void should_not_accept_illegal_length(){
        String legalFirstname = "valid", legalLastName = "valid";
        StringBuilder illegalFirstName = new StringBuilder(), illegalLastName = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            illegalFirstName.append("f");
            illegalLastName.append("l");
        }
        invalidInput(illegalFirstName.toString(), legalLastName, illegalFirstName.toString() + ": Should be rejected for Illegal length.");
        invalidInput(legalFirstname, illegalLastName.toString(), illegalLastName.toString() + ": Should be rejected for Illegal length.");
    }

    @Test
    public void should_not_accept_illegal_characters(){
        String legalFirstname = "valid", legalLastName = "valid";
        String illegalFirstName = "&^", illegalLastName = "&^";
        invalidInput(illegalFirstName, legalLastName, illegalFirstName + ": Should be rejected for Illegal characters.");
        invalidInput(legalFirstname, illegalLastName, illegalLastName + ": Should be rejected for Illegal characters.");
    }

    public String[][] parametersForValid() {
        return new String[][]{{"val", "id"}, {"va-l", "id"}, {"val", "i-d"}, {"va-l", "i-d"}, {"va--l", "i--d"}};
    }

    private void invalidInput(String firstName, String lastName, String message){
        try {
            new User(firstName, lastName);
            fail(message);
        }catch (IllegalArgumentValidationException e){
        }
    }
}