package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto;

public interface Event extends DTO{
    String type();
    String requestId();
}
