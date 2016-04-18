package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.data_extraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.ItemDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ItemService {

    @Autowired
    ItemDataShortcut dataShortcut;

    @RequestMapping(value = "/most_popular_item", method = GET)
    public ResponseEntity<ItemDTO> getMostPopularItem(
            @RequestParam("since") String since) {

        LocalDateTime dateTime = LocalDateTime.parse(since);
        Timestamp timestamp = new Timestamp(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        Optional<Item> maybeItem = dataShortcut.getMostPopularItemSince(timestamp);

        return maybeItem.map(item -> new ResponseEntity<>(new ItemDTO(item), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
