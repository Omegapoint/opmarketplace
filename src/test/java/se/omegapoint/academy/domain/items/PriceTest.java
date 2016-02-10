package se.omegapoint.academy.domain.items;

import org.junit.Test;

import static org.junit.Assert.fail;


public class PriceTest {

    @Test
    public void valid(){
        try {
            new Price("1");
        }catch (IllegalArgumentException e){
            fail("1: " + e.getMessage());
        }

        try {
            new Price("10");
        }catch (IllegalArgumentException e){
            fail("10: " + e.getMessage());
        }

        try {
            new Price("0");
        }catch (IllegalArgumentException e){
            fail("0: " + e.getMessage());
        }

        try {
            new Price("1.1");
        }catch (IllegalArgumentException e){
            fail("1.1: " + e.getMessage());
        }

        try {
            new Price("11.11");
        }catch (IllegalArgumentException e){
            fail("11.11: " + e.getMessage());
        }
    }

    @Test
    public void should_reject_multiple_dots(){
        try {
            new Price("1.1.1");
            fail("1.1.1: Multiple dots not rejected.");
        }catch (IllegalArgumentException e){
        }
    }

    @Test
    public void should_reject_letters(){
        try {
            new Price("a");
            fail("a: Letters not rejected.");
        }catch (IllegalArgumentException e){
        }
    }

    @Test
    public void should_reject_negative(){
        try {
            new Price("-1");
            fail("-1: Negative values not rejected.");
        }catch (IllegalArgumentException e){
        }
    }

    @Test
    public void should_reject_no_number_right_of_dot(){
        try {
            new Price("1.");
            fail("1.: No decimal value not rejected.");
        }catch (IllegalArgumentException e){
        }
    }

    @Test
    public void should_reject_too_large_number(){
        String too_large_number = String.valueOf(Math.round(Math.pow(10, Price.EXPONENT_LIMIT)));
        try {
            new Price(too_large_number);
            fail(too_large_number + ": Large number was not rejected.");
        }catch (IllegalArgumentException e){
        }
    }

    @Test
    public void should_reject_too_high_precision(){
        String too_high_precision = "1.";
        for (int i = 0; i < Price.DECIMAL_POINT_LIMIT+1; i++) {
            too_high_precision += "0";
        }
        try {
            new Price(too_high_precision);
            fail(too_high_precision + ": High precision was not rejected.");
        }catch (IllegalArgumentException e){
        }
    }
}