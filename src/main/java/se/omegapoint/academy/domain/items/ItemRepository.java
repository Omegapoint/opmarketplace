package se.omegapoint.academy.domain.items;

import se.omegapoint.academy.persistance.items.ItemEntity;
import se.omegapoint.academy.persistance.items.ItemJPARepository;

import java.util.UUID;

public class ItemRepository {

    private final ItemJPARepository items;

    public ItemRepository(ItemJPARepository itemJPARepository){
        this.items = itemJPARepository;
    }

    public void addItem(Item item){
        items.save(new ItemEntity(item.id(), item.title(), item.description(), item.price()));
    }

    public Item item(String id){
        ItemEntity itemEntity = items.getOne(id);
        return new Item(UUID.fromString(itemEntity.getId()), itemEntity.getTitle(), itemEntity.getDescription(), itemEntity.getPrice(), null);
    }
}
