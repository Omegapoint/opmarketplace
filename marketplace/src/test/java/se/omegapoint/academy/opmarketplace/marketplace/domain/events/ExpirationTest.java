package se.omegapoint.academy.opmarketplace.marketplace.domain.events;

import org.junit.Test;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Expiration;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ExpirationTest {

    @Test
    public void testTime() throws Exception {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Expiration expiration = new Expiration(time);
        assertEquals(time, expiration.time());
    }

    @Test
    public void testHasExpired() throws Exception {
        Timestamp notExpiredTime = new Timestamp(System.currentTimeMillis() + 1000);
        Expiration notExpired = new Expiration(notExpiredTime);
        Timestamp expiredTime = new Timestamp(System.currentTimeMillis() - 1);
        Expiration expired = new Expiration(expiredTime);
        assertFalse(notExpired.hasExpired());
        assertTrue(expired.hasExpired());
    }
}