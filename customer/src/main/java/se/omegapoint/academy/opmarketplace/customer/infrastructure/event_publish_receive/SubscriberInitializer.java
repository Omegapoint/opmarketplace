package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

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
import java.util.List;

public class SubscriberInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${subscription.url}")
    private URL subscriptionUrl;

    @Value("#{'${subscription.channels}'.split(',')}")
    private List<String> channels;

    @Value("${event.receiver.url}")
    private URL eventReceiverUrl;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        channels.forEach(this::subscribe);
        System.out.println("----- SUBSCRIPTION FINISHED -----");
    }

    private void subscribe(String channel) {
        try {
            URIBuilder uriBuilder = new URIBuilder(subscriptionUrl.toURI())
                    .addParameter("channel", channel)
                    .addParameter("endpoint", eventReceiverUrl.toString());

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                System.out.printf("Subscribed %s to channel %s on %s%n", eventReceiverUrl, channel, subscriptionUrl);
            } else {
                System.out.printf("Failed to subscribe %s to channel %s on %s%n", eventReceiverUrl, channel, subscriptionUrl);
            }
            response.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
