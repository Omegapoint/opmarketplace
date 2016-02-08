package se.omegapoint.academy.web.items;

public class ItemModel {
    private final String id;
    private final String title;
    private final String description;
    private final int price;

    public ItemModel(String id, String title, String description, int price) {
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
