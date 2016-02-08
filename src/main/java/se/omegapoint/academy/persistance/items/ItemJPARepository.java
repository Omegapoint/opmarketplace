package se.omegapoint.academy.persistance.items;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJPARepository extends JpaRepository<ItemEntity, String>{
}
