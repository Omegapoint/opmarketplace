package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import java.util.List;

public interface ItemRepository {
    void addItem(Item item);

    void deleteItem(String id);

    void updateItem(Item item);

    Item item(String id);

    List<Item> findByTitle(Title title);
}
