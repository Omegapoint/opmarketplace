package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.data_adapters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter {

    @Value("${url.customer.users}")
    private URI fetchUsersURL;

    CloseableHttpClient httpClient;
    ObjectMapper objectMapper;

    public UserAdapter() {
        httpClient = HttpClients.createDefault();
        objectMapper = new ObjectMapper();
    }

    public List<String> fetchList(LocalDateTime memberSince, int minSpend) {
        try {

            URIBuilder builder = new URIBuilder(fetchUsersURL);
            HttpGet httpGet = new HttpGet(builder
                    .addParameter("member_since", memberSince.toString())
                    .addParameter("min_spend", String.valueOf(minSpend))
                    .build());

            CloseableHttpResponse response = httpClient.execute(httpGet);
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<String>>(){});

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
