package se.omegapoint.academy.domain.items;

import org.junit.Test;

import static org.junit.Assert.*;

public class TitleTest {

    @Test
    public void valid(){
        validInput("valid", "valid");
        validInput("validvalidvalidvalidvalidvalidvalidvalidvalidvalid", "validvalidvalidvalidvalidvalidvalidvalidvalidvalid");
        validInput("valid.", "valid.");
        validInput("valid1", "valid1");
    }

    @Test
    public void too_long(){
        invalidInput("validvalidvalidvalidvalidvalidvalidvalidvalidvalidv", "Illegal length should be rejected.");
    }

    @Test
    public void invalid_characters(){
        invalidInput("<", "Illegal characters should be rejected");
    }

    private void validInput(String expected, String input){
        try {
            assertEquals(expected, new Title(input).text());
        }catch (Exception e){
            fail(input+": " + e.getMessage());
        }
    }

    private void invalidInput(String input, String message){
        try {
            new Title(input);
            fail(input+": " + message);
        }catch (Exception e){
        }
    }

}