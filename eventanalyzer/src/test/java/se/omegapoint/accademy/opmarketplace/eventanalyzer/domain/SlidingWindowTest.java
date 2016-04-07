package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingWindowTest {

    @Test
    public void window_should_accept_rate() throws Exception {
        int size = 20; long timeInterval = 100;
        SlidingWindow window = new SlidingWindow(size, timeInterval);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            assertFalse(window.isFull());
        }
    }

    @Test
    public void window_should_not_accept_rate() throws Exception {
        int size = 20; long timeInterval = 100;
        SlidingWindow window = new SlidingWindow(size, timeInterval);
        for (int i = 0; i < 100; i++) {
            if (i < 20) {
                assertFalse(window.isFull());
            } else {
                assertTrue(window.isFull());
            }
        }
    }

    @Test
    public void window_should_accept_fast_rate_followed_by_pause_followed_by_fast_rate() throws Exception {
        int size = 20; long timeInterval = 100;
        SlidingWindow window = new SlidingWindow(size, timeInterval);
        for (int i = 0; i < 20; i++) {
            assertFalse(window.isFull());
        }
        Thread.sleep(200);
        for (int i = 0; i < 20; i++) {
            assertFalse(window.isFull());
        }
    }
}