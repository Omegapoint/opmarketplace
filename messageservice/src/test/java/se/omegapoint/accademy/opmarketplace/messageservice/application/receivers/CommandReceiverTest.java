package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CommandReceiverTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCommandIsAccepted() throws Exception {
        mockMvc.perform(post("/command")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", "kebabpizza")
                .content("{\"eventType\": \"AccountCreated\",\"allow\": \"false\"}")
        )
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void testCommandIsNotAcceptedWithoutContent() throws Exception {
        mockMvc.perform(post("/command")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", "kebabpizza")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testCommandIsNotAcceptedWithoutToken() throws Exception {
        mockMvc.perform(post("/command")
                .content("{\"eventType\": \"AccountCreated\",\"allow\": \"false\"}")

        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testCommandIsUnauthorizedWithoutCorrectToken() throws Exception {
        mockMvc.perform(post("/command")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", "123123")
                .content("{\"eventType\": \"AccountCreated\",\"allow\": \"false\"}")
        )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }
}