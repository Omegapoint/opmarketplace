package se.omegapoint.academy.domain.items;

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
        inValidInput("<");
        inValidInput("invalid<invalid");
        inValidInput(">");
        inValidInput("$");
        inValidInput("-");
        inValidInput("}");
    }

    private void validInput(String expected, String input){
        try {
            assertEquals(expected, new Description(input).text());
        }catch (Exception e){
            fail(input+": " + e.getMessage());
        }
    }

    private void inValidInput(String input){
        try {
            new Description(input);
            fail(input+": contains invalid characters.");
        }catch (Exception e){
        }
    }

}