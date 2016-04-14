package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemOrderEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ItemOrderJPARepository extends JpaRepository<ItemOrderEntity, String> {

    List<ItemOrderEntity> findById(String id);

    List<ItemOrderEntity> findByOrderId(String orderId);

    List<ItemOrderEntity> findByTimeGreaterThan(Timestamp time);
}
