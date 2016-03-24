package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import org.junit.Test;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class PriceTest {

    @Test
    public void valid(){
        validInput("1.00", "1");
        validInput("0.10", "0.1");
        validInput("0.01", "0.01");
        validInput("10.00", "10");
        validInput("100.00", "100");
        validInput("0.00", "0.00");
        validInput("1.10", "1.1");
        validInput("11.11", "11.11");
    }

    private void validInput(String expected, String input){
        try {
            assertEquals(expected, new Price(input).amount());
        }catch (IllegalArgumentException e){
            fail(input+": " + e.getMessage());
        }
    }

    @Test
    public void should_reject_multiple_dots(){
        invalidInput("1.1.1", "Multiple dots not rejected.");
    }

    @Test
    public void should_reject_letters(){
        invalidInput("a", "Letters not rejected.");
    }

    @Test
    public void should_reject_negative(){
        invalidInput("-1", "Negative values not rejected.");
    }

    @Test
    public void should_reject_no_number_right_of_dot(){
        invalidInput("1.", "No decimal value not rejected.");
    }

    @Test
    public void should_reject_too_large_number(){
        invalidInput("1000000000", "Large number was not rejected.");
    }

    @Test
    public void should_reject_too_high_precision(){
        String too_high_precision = "1.";
        for (int i = 0; i < 3; i++) {
            too_high_precision += "0";
        }
        invalidInput(too_high_precision, "High precision was not rejected.");
    }

    private void invalidInput(String input, String message){
        try {
            new Price(input);
            fail(input + ": " + message);
        }catch (Exception e){
        }
    }
}