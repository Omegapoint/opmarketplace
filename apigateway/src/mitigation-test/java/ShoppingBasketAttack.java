import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemReservationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
public class ShoppingBasketAttack {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private int uuidIndex;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void reservationAttackWithOneUser() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new CheckAvailableObjects(), 0, 1000);
        timer.schedule(new PutInShoppingBasket(), 200, 500);

        Thread.sleep(30000);
    }

    @Test
    public void reservationAttackWithMultipleUsers() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new CheckAvailableObjects(), 0, 1000);
        timer.schedule(new PutInShoppingBasketMultipleUsers(), 100, 333);

        Thread.sleep(30000);
    }

    private class CheckAvailableObjects extends TimerTask {
        @Override
        public void run() {
            RestTemplate template = new RestTemplate();
            ItemDTO[] items = template.getForObject("http://localhost:8002/items/search?query=hodor", ItemDTO[].class);
            System.out.println(items.length);
        }
    }

    private class PutInShoppingBasket extends TimerTask {
        @Override
        public void run() {
            RestTemplate template = new RestTemplate();
            try {
                template.postForEntity("http://localhost:8002/items/reserve", new ItemReservationRequestedDTO(getNextUUID(), 1, "malicious1339@test.com"), String.class);
            } catch (Exception e) {}
        }
    }

    private class PutInShoppingBasketMultipleUsers extends TimerTask {
        @Override
        public void run() {
            RestTemplate template = new RestTemplate();
            try {
//                System.out.println("malicious" + uuidIndex + "@test.com");
                String uuid = getNextUUID();
//                System.out.println(uuid);
                template.postForEntity("http://localhost:8002/items/reserve", new ItemReservationRequestedDTO(uuid, 1, "malicious" + uuidIndex + "@test.com"), String.class);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    private String getNextUUID() {
        String base = "00000000-0000-0000-0000-00000000000";
        String suffix = Integer.toString(uuidIndex);
        uuidIndex++;
        return base.substring(0, base.length() - suffix.length()) + suffix;
    }
}
