package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import java.time.LocalDateTime;

public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item getItemFromId(String id){
        return itemRepository.item(id);
    }

    public void addItem(String title, String description, String price){
        Item newItem = new Item(title, description, price, LocalDateTime.now().plusDays(7));
        itemRepository.addItem(newItem);
    }

    public void addItem(String id, String title, String description, String price){
        Item newItem = new Item(id, title, description, price, LocalDateTime.now().plusDays(7));
        itemRepository.addItem(newItem);
    }

    public void changeTitle(String id, String title){
        Item item = getItemFromId(id);
        item = item.withNewTitle(title);
        itemRepository.updateItem(item);
    }
}
