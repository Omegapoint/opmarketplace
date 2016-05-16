package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemReservedEntity;

import java.util.List;

public interface ItemReservedJPARepository extends JpaRepository<ItemReservedEntity, Long> {

    List<ItemReservedEntity> findByItemId(String id);
}
