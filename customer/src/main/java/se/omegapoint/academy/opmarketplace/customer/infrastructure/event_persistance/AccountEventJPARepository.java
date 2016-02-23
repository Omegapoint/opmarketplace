package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountEventJPARepository extends JpaRepository<DomainEventEntity, Long> {

    List<DomainEventEntity> findByAggregateIdOrderByTime(String aggregateId);
}
