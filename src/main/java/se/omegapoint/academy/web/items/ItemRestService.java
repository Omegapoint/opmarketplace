package se.omegapoint.academy.web.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.ItemService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/items")
public class ItemRestService {

    private final ItemService itemService;

    @Autowired
    public ItemRestService(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemModel> item(@RequestParam("id") final String id) {
        try {
            Item item = itemService.getItemFromId(id);
            return ResponseEntity.ok(new ItemModel(item.id(), item.title().text(), item.description(), item.price().amount()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity item(@RequestParam("title") final String title, @RequestParam("description") final String description, @RequestParam("price") final String price) {
        try {
            itemService.addItem(title, description, price);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
