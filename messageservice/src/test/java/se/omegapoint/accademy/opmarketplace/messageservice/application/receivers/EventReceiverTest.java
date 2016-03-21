package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.accademy.opmarketplace.messageservice.Application;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.RemoteEvent;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EventReceiverTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testReceiveEvent() throws Exception {
        RemoteEvent domainEvent = getExampleDomainEvent();
        String postContent = new ObjectMapper().writeValueAsString(domainEvent);

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .param("channel", "one")
                .content(postContent)
        )
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    private RemoteEvent getExampleDomainEvent() {
        return new RemoteEvent("typ1", "some data", new Timestamp(1337));
    }
}