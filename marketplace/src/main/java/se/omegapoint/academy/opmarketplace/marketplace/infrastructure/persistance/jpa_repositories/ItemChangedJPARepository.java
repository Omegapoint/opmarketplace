package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemChangedEntity;

import java.util.List;

public interface ItemChangedJPARepository extends JpaRepository<ItemChangedEntity, Long> {

    List<ItemChangedEntity> findByTitleContainingOrDescriptionContainingAllIgnoreCase(String titleQuery, String descriptionQuery);

    List<ItemChangedEntity> findById(String id);

}
