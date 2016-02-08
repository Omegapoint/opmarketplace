package se.omegapoint.academy.domain.items;

public interface ItemRepository {
    void addItem(Item item);

    Item item(String id);
}
