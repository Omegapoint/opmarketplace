package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import org.junit.Test;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Credit;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class CreditTest {

    @Test
    public void should_accept_0(){
        new Credit(0);
    }

    @Test
    public void should_accept_999(){
        new Credit(999);
    }

    @Test(expected = IllegalArgumentValidationException.class)
    public void should_not_accept_negative(){
        new Credit(-1);
    }

    @Test(expected = IllegalArgumentValidationException.class)
    public void should_not_accept_1000(){
        new Credit(1000);
    }
}