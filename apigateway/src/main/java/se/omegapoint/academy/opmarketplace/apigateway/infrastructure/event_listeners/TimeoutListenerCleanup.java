package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import reactor.bus.registry.Registration;

import java.util.TimerTask;

public class TimeoutListenerCleanup extends TimerTask {

    private final Registration registrationToCancel;

    public TimeoutListenerCleanup(Registration registrationToCancel){
        this.registrationToCancel = registrationToCancel;
    }
    @Override
    public void run() {
        if (!this.registrationToCancel.isCancelled()) {
            this.registrationToCancel.cancel();
        }
    }
}
