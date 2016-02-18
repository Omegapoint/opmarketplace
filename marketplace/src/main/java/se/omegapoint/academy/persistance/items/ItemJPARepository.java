package se.omegapoint.academy.persistance.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJPARepository extends JpaRepository<ItemEntity, String>{

    List<ItemEntity> findByTitle(String title);
}
