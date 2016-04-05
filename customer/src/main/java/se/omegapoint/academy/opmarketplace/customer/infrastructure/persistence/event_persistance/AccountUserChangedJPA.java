package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountUserChangedModel;

import java.util.List;

public interface AccountUserChangedJPA extends JpaRepository<AccountUserChangedModel, Long> {

    List<AccountUserChangedModel> findByEmailOrderByTime(String aggregateId);
}
