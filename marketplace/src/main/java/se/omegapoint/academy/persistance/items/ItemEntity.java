package se.omegapoint.academy.persistance.items;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String price;

    protected ItemEntity(){}

    public ItemEntity(final String id, final String title, final String description, final String price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setPrice(String price) {
        this.price = price;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setTitle(String title) {
        this.title = title;
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

    public String getPrice() {
        return price;
    }
}
