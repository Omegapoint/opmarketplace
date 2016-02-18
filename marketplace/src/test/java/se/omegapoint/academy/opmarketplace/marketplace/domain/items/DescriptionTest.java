package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import org.junit.Test;

import static org.junit.Assert.*;

public class DescriptionTest {

    @Test
    public void valid(){
        validInput("valid", "valid");
        validInput("valid.", "valid.");
        validInput(",valid", ",valid");
        validInput("%valid", "%valid");
        validInput("val&id", "val&id");
        validInput("val id", "val id");
    }

    @Test
    public void should_reject_invalid_characters(){
        String error_message = "Invalid characters were not rejected.";
        inValidInput("<", error_message);
        inValidInput("invalid<invalid", error_message);
        inValidInput(">", error_message);
        inValidInput("$", error_message);
        inValidInput("-", error_message);
        inValidInput("}", error_message);
    }

    @Test
    public void should_reject_invalid_length(){
        StringBuilder long_sb = new StringBuilder();
        for (int i = 0; i < 5001; i++) {
            long_sb.append('a');
        }
        inValidInput(long_sb.toString(), "Long description was not rejected.");
    }

    private void validInput(String expected, String input){
        try {
            assertEquals(expected, new Description(input).text());
        }catch (Exception e){
            fail(input+": " + e.getMessage());
        }
    }

    private void inValidInput(String input, String message){
        try {
            new Description(input);
            fail(input + ": " + message);
        }catch (Exception e){
        }
    }

}