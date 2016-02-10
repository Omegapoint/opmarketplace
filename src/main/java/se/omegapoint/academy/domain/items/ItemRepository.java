package se.omegapoint.academy.domain.items;

import java.util.List;

public interface ItemRepository {
    void addItem(Item item);

    Item item(String id);

    List<Item> findByTitle(Title title);
}
