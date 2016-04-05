package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountDeletedModel;

import java.util.List;

public interface AccountDeletedJPA extends JpaRepository<AccountDeletedModel, Long> {

    List<AccountDeletedModel> findByEmailOrderByTime(String email);
}
