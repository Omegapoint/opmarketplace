package se.omegapoint.academy.domain.items;

import org.junit.Test;

import static org.junit.Assert.*;

public class TitleTest {

    @Test
    public void valid(){
        String title = "";
        for (int i = 0; i < Title.MAX_LENGTH; i++) {
            title+= "a";
        }
        try{
            new Title(title);
        }catch (IllegalArgumentException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void too_long(){
        String title = "";
        for (int i = 0; i < Title.MAX_LENGTH + 1; i++) {
            title+= "a";
        }
        try{
            new Title(title);
            fail("Illegal length should be rejected.");
        }catch (IllegalArgumentException e){
            assertEquals(Title.ILLEGAL_LENGTH, e.getMessage());
        }
    }

    @Test
    public void invalid_characters(){
        String title = "<";
        try{
            new Title(title);
            fail("Illegal characters should be rejected.");
        }catch (IllegalArgumentException e){
            assertEquals(Title.ILLEGAL_CHARACTERS, e.getMessage());
        }
    }

}