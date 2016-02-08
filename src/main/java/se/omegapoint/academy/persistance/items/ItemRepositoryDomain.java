package se.omegapoint.academy.persistance.items;

import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.ItemRepository;
import se.omegapoint.academy.persistance.items.ItemEntity;
import se.omegapoint.academy.persistance.items.ItemJPARepository;

import java.util.UUID;

public class ItemRepositoryDomain implements ItemRepository {

    private final ItemJPARepository items;

    public ItemRepositoryDomain(ItemJPARepository itemJPARepository){
        this.items = itemJPARepository;
    }

    @Override
    public void addItem(Item item){
        items.save(new ItemEntity(item.id(), item.title(), item.description(), item.price()));
    }

    @Override
    public Item item(String id){
        ItemEntity itemEntity = items.getOne(id);
        return new Item(UUID.fromString(itemEntity.getId()), itemEntity.getTitle(), itemEntity.getDescription(), itemEntity.getPrice(), null);
    }
}
