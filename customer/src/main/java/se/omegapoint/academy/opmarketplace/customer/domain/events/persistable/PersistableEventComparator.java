package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;

import java.util.Comparator;

public class PersistableEventComparator implements Comparator<PersistableEvent> {
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
