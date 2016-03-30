package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import java.util.Comparator;

public class PersistableEventComarator implements Comparator<PersistableEvent> {
    @Override
    public int compare(PersistableEvent o1, PersistableEvent o2) {
        if (o1.timestamp().after(o2.timestamp())){
            return 1;
        } else if (o1.timestamp().before(o2.timestamp())){
            return -1;
        }
        return 0;
    }
}
