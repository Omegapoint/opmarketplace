package se.omegapoint.academy.web.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.ItemService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/items")
public class ItemRestService {

    private final ItemService itemService;

    @Autowired
    public ItemRestService(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemModel> item(@RequestParam("id") final String itemId) {
        try {
            Item item = itemService.getItemFromId(itemId);
            return ResponseEntity.ok(new ItemModel(item.id(), item.title(), item.description(), item.price()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
