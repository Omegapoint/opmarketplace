package se.omegapoint.academy.opmarketplace.apigateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.apache.http.impl.client.CloseableHttpClient;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.AccountDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRetrieverService implements UserDetailsService{

    private final CloseableHttpClient http;
    private final ObjectMapper json = new ObjectMapper();

    public AccountRetrieverService(CloseableHttpClient http) {
        this.http = notNull(http);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            HttpGet httpGet = new HttpGet("http://localhost:8002/accounts/?email=" + username);
            httpGet.addHeader("Content-Type", "application/json");
            CloseableHttpResponse response = http.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                String content = EntityUtils.toString(response.getEntity());
                return new AccountAuth(json.readValue(content, AccountDTO.class));
            }
            else{
                throw new UsernameNotFoundException(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanup() throws IOException {
        http.close();
    }


}
