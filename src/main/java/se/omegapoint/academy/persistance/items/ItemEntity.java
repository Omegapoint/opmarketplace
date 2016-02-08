package se.omegapoint.academy.persistance.items;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private int price;

    protected ItemEntity(){}

    public ItemEntity(final String id, final String title, final String description, final int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
