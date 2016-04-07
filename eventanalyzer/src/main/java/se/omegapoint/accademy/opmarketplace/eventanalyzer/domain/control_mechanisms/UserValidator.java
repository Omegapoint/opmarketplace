package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.control_mechanisms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserValidator {

    @Value("${url.customer.users}")
    private URI fetchUsersURL;

    CloseableHttpClient httpClient;
    ObjectMapper objectMapper;

    public UserValidator() {
        httpClient = HttpClients.createDefault();
        objectMapper = new ObjectMapper();
    }

    public List<String> fetchList(LocalDateTime memberSince) {
        try {

            URIBuilder builder = new URIBuilder(fetchUsersURL);
            HttpGet httpGet = new HttpGet(builder
                    .addParameter("member_since", memberSince.toString())
                    // TODO: 07/04/16 Implement min spend
                    .addParameter("min_spend", "0")
                    .build());

            CloseableHttpResponse response = httpClient.execute(httpGet);
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<String>>(){});

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
