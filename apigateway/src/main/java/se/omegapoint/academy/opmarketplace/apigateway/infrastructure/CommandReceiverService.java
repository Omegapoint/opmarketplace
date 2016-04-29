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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands.DefaultSearchResultDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands.DisableFeatureDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands.RateLimitFeatureDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands.ValidateUsersDTO;

import java.io.IOException;
import java.util.Arrays;

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
                    DisableFeatureDTO disableCommand = mapper.readValue(data, DisableFeatureDTO.class);
                    ruleEngine.disableEvent(disableCommand.eventName, disableCommand.noSeconds);
                    break;
                case ValidateUsersDTO.TYPE:
                    ValidateUsersDTO validateUsersCommand = mapper.readValue(data, ValidateUsersDTO.class);
                    System.out.println(Arrays.toString(validateUsersCommand.users.toArray()));
                    ruleEngine.allowUsers(validateUsersCommand.users, validateUsersCommand.noSeconds, validateUsersCommand.onlyImportantUsers);
                    break;
                case RateLimitFeatureDTO.TYPE:
                    RateLimitFeatureDTO rateLimitFeatureCommand = mapper.readValue(data, RateLimitFeatureDTO.class);
                    ruleEngine.addRateLimiting(rateLimitFeatureCommand.interval, rateLimitFeatureCommand.noSeconds);
                case DefaultSearchResultDTO.TYPE:
                    DefaultSearchResultDTO defaultSearchResultCommand = mapper.readValue(data, DefaultSearchResultDTO.class);
                    ruleEngine.setDefaultSearchResult(defaultSearchResultCommand.item, defaultSearchResultCommand.noSeconds);
                    break;
                default:
                    System.err.println("Received unknown command: " + commandType);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }
}
