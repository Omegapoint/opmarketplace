package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.opmarketplace.apigateway.application.RuleEngine;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands.DisableFeatureDTO;

import java.io.IOException;

@RestController
@RequestMapping("/command")
public class CommandReceiverService {

    @Autowired
    RuleEngine ruleEngine;

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> commandReceiver(@RequestBody JsonNode jsonNode) {
        String commandType = jsonNode.get("type").textValue();
        String data = jsonNode.get("data").toString();

        try {
            switch (commandType) {
                case DisableFeatureDTO.TYPE:
                    DisableFeatureDTO command = mapper.readValue(data, DisableFeatureDTO.class);
                    ruleEngine.deny(command.eventName, command.noSeconds);
                    break;
                default:
                    System.err.println("Received unknown command: " + commandType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }
}
