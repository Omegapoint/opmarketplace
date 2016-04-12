package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditDeposited;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreditDepositedModel;

import java.util.List;

public interface AccountCreditDepositedJPA extends JpaRepository<AccountCreditDepositedModel, Long> {

    List<AccountCreditDepositedModel> findByEmailOrderByTime(String email);
}
