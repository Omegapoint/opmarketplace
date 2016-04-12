package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreditWithdrawnModel;

import java.util.List;

public interface AccountCreditWithdrawnJPA extends JpaRepository<AccountCreditWithdrawnModel, Long> {

    List<AccountCreditWithdrawnModel> findByEmailOrderByTime(String email);
}
