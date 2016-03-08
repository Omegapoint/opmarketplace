package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.accademy.opmarketplace.messageservice.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SubscriptionReceiverTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSubscriptionIsAccepted() throws Exception {
        mockMvc.perform(post("/subscribe")
                .param("channel", "one")
                .param("endpoint", "http://test.com")
        )
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void testMalformattedURLIsRejected() throws Exception {
        mockMvc.perform(post("/subscribe")
                .param("channel", "one")
                .param("endpoint", "//test.com")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testSubscribeAllIsAccepted() throws Exception {
        mockMvc.perform(post("/subscribe_all")
                .param("endpoint", "http://test.com")
                .param("token", "kebabpizza")
        )
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void testSubscribeAllIsRejectedWithoutToken() throws Exception {
        mockMvc.perform(post("/subscribe_all")
                .param("endpoint", "http://test.com")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testSubscribeAllIsUnauthorizedWithoutCorrectToken() throws Exception {
        mockMvc.perform(post("/subscribe_all")
                .param("token", "123123")
                .param("endpoint", "http://test.com")
        )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }
}