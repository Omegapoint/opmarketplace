package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.data_adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.json_representations.ItemDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class ItemAdapter {

    @Value("${url.marketplace.items}")
    private URI fetchImportantItemUrl;

    CloseableHttpClient httpClient;
    ObjectMapper objectMapper;

    public ItemAdapter() {
        httpClient = HttpClients.createDefault();
        objectMapper = new ObjectMapper();
    }

    public ItemDTO fetchMostImportantItemSince(LocalDateTime since) {
        try {
            URIBuilder builder = new URIBuilder(fetchImportantItemUrl);
            HttpGet httpGet = new HttpGet(builder
                    .addParameter("since", since.toString())
                    .build());

            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return objectMapper.readValue(response.getEntity().getContent(), ItemDTO.class);
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        // TODO: 18/04/16 What to do with null?
        return null;
    }
}
