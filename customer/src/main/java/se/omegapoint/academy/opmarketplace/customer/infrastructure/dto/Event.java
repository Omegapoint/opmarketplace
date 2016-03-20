package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto;

public interface Event extends DTO{
    String type();
    String requestId();
}
