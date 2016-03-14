package se.omegapoint.accademy.opmarketplace.eventanalyzer;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class SubscriberInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${subscription.url}")
    private URL subscriptionUrl;

    @Value("${event.receiver.url}")
    private URL eventReceiverUrl;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        subscribeAll();
        System.out.println("----- SUBSCRIPTION FINISHED -----");
    }

    private void subscribeAll() {
        try {
            URIBuilder uriBuilder = new URIBuilder(subscriptionUrl.toURI())
                    .addParameter("token", "kebabpizza")
                    .addParameter("endpoint", eventReceiverUrl.toString());

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                System.out.printf("Subscribed %s to all channels on %s%n", eventReceiverUrl, subscriptionUrl);
            } else {
                System.out.printf("Failed to subscribe %s to all channels on %s%n", eventReceiverUrl, subscriptionUrl);
            }
            response.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
