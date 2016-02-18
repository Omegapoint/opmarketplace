package se.omegapoint.academy.opmarketplace.marketplace.web.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.ItemService;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
            return ResponseEntity.ok(new ItemModel(item.id(), item.title().text(), item.description().text(), item.price().amount()));
        } catch (IllegalArgumentException |  IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity item(@RequestParam("title") final String title, @RequestParam("description") final String description, @RequestParam("price") final String price) {
        try {
            itemService.addItem(title, description, price);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException |  IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity item(@RequestParam("id") final String id, @RequestParam("title") final String title) {
        try {
            itemService.changeTitle(id, title);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException |  IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
