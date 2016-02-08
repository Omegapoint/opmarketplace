package se.omegapoint.academy.domain.items;

import se.omegapoint.academy.persistance.items.ItemRepositoryDomain;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItemService {

    private final ItemRepository itemRepository;


    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item getItemFromId(String id){
        return itemRepository.item(id);
    }

    public void addItem(String title, String description, int price){
        Item newItem = new Item(title, description, price, LocalDateTime.now().plusDays(7));
        itemRepository.addItem(newItem);
    }

    public void addItem(UUID id, String title, String description, int price){
        Item newItem = new Item(id, title, description, price, LocalDateTime.now().plusDays(7));
        itemRepository.addItem(newItem);
    }
}
