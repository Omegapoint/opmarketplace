package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemCreatedEntity;

import java.util.List;

public interface ItemCreatedJPARepository extends JpaRepository<ItemCreatedEntity, Long>{

    List<ItemCreatedEntity> findByTitleContainingOrDescriptionContainingAllIgnoreCase(String titleQuery, String descriptionQuery);

    List<ItemCreatedEntity> findById(String id);
}
