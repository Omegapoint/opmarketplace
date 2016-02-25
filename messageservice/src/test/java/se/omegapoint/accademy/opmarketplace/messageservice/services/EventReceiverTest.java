package se.omegapoint.accademy.opmarketplace.messageservice.services;

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
import se.omegapoint.accademy.opmarketplace.messageservice.models.DomainEventModel;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.junit.Assert.*;



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
    public void testGetExampleEvent() throws Exception {
        DomainEventModel domainEvent = getExampleDomainEvent();

        mockMvc.perform(get("/event"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(domainEvent)));
    }

    @Test
    public void testReceiveEvent() throws Exception {
        DomainEventModel domainEvent = getExampleDomainEvent();
        String postContent = new ObjectMapper().writeValueAsString(domainEvent);

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postContent)
        )
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    private DomainEventModel getExampleDomainEvent() {
        return new DomainEventModel(
                "12345",
                "Exempelaggregat",
                "Testevent",
                "Data 123",
                new Timestamp(1337));
    }
}