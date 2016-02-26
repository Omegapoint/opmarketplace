package se.omegapoint.academy.opmarketplace.customer.domain.events;

public interface AggregateModification{
    String aggregateMemberId();
    String aggregateName();
}
