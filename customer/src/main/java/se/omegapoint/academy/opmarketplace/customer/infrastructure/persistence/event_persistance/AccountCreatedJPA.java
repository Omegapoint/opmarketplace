package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreatedModel;

import java.sql.Timestamp;
import java.util.List;

public interface AccountCreatedJPA extends JpaRepository<AccountCreatedModel, Long> {

    List<AccountCreatedModel> findByEmailOrderByTime(String email);
    List<AccountCreatedModel> findByTimeLessThan(Timestamp time);
}
