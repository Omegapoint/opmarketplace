package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import java.time.LocalTime;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SlidingWindow {

    int counter = 0;
    final long milliSecondInterval;
    LocalTime[] window;

    public SlidingWindow(int size, long milliSecondInterval) {
        this.milliSecondInterval = milliSecondInterval;
        window = new LocalTime[size];
    }

    public boolean isFull() {
        LocalTime now = LocalTime.now();
        LocalTime then = window[counter % window.length];
        window[counter % window.length] = now;
        counter++;

        return then != null && now.minusNanos(MILLISECONDS.toNanos(milliSecondInterval)).isBefore(then);
    }
}
