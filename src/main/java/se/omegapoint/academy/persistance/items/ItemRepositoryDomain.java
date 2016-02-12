package se.omegapoint.academy.persistance.items;

import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.ItemRepository;
import se.omegapoint.academy.domain.items.Title;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemRepositoryDomain implements ItemRepository {

    private final ItemJPARepository items;

    public ItemRepositoryDomain(ItemJPARepository itemJPARepository){
        this.items = itemJPARepository;
    }

    @Override
    public void addItem(Item item){
        items.save(new ItemEntity(item.id(), item.title().text(), item.description().text(), item.price().amount()));
    }

    @Override
    public Item item(String id){
        ItemEntity itemEntity = items.getOne(id);
        return new Item(UUID.fromString(itemEntity.getId()), itemEntity.getTitle(), itemEntity.getDescription(), itemEntity.getPrice(), null);
    }

    @Override
    public List<Item> findByTitle(Title title) {
        return items.findByTitle(title.text()).stream().map(itemEntity -> new Item(UUID.fromString(itemEntity.getId()), itemEntity.getTitle(), itemEntity.getDescription(), itemEntity.getPrice(), null))
                .collect(Collectors.<Item> toList());
    }

}
