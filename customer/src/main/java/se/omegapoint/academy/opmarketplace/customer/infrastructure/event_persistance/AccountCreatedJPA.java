package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreatedModel;

import java.util.List;

public interface AccountCreatedJPA extends JpaRepository<AccountCreatedModel, Long> {

    List<AccountCreatedModel> findByAggregateMemberIdOrderByTime(String aggregateId);
}
