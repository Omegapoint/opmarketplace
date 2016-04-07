package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemOrderReversedEntity;

import java.util.List;

public interface ItemOrderReverseJPARepository extends JpaRepository<ItemOrderReversedEntity, Long> {

    List<ItemOrderReversedEntity> findByItemId(String id);

}
