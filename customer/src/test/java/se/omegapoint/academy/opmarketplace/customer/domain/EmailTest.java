package se.omegapoint.academy.opmarketplace.customer.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;


import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class EmailTest {

    @Test
    @Parameters
    public void valid(String email){
        try {
            assertEquals(email, new Email(email).address());
        }catch (Exception e){
            fail(email+": " + e.getMessage());
        }
    }

    @Test
    public void should_not_accept_illegal_length(){
        StringBuilder input = new StringBuilder();
        input.append("a@");
        for (int i = 0; i < 253; i++) {
            input.append("a");
        }
        invalidInput(input.toString(), "Should be rejected for Illegal length.");
    }

    @Test
    public void should_not_accept_illegal_local_part_length(){
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 65; i++) {
            input.append("a");
        }
        input.append("@a");
        invalidInput(input.toString(), "Should be rejected for Illegal format since local part of mail is too long.");
    }

    public String[] parametersForValid() {
        //return new String[]{"a@b", "test@test.com", "hej.pa.dig@hej", "123.2432.34253.324@1234:232"};
        return new String[]{"test@test.com"};
    }

    private void invalidInput(String input, String message){
        try {
            new Email(input);
            fail(input+": " + message);
        }catch (IllegalArgumentValidationException e){
        }
    }

}