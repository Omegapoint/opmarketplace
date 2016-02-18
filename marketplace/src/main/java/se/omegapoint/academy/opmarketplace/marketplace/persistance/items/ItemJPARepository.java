package se.omegapoint.academy.opmarketplace.marketplace.persistance.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJPARepository extends JpaRepository<ItemEntity, String>{

    List<ItemEntity> findByTitle(String title);
}
