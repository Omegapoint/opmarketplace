package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void valid(){
        validInput("test@test.com");
    }

    private void validInput(String input){
        try {
            assertEquals(input, new Email(input).address());
        }catch (Exception e){
            fail(input+": " + e.getMessage());
        }
    }

    private void invalidInput(String input, String message){
        try {
            new Email(input);
            fail(input+": " + message);
        }catch (IllegalArgumentValidationException e){
        }
    }

}