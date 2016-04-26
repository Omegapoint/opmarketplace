package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
public class LoaderVerifyer {

    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
    public String getRobots(HttpServletRequest request) {
        return (Arrays.asList("mysite.com", "www.mysite.com").contains(request.getServerName())) ?
                "robotsAllowed" : "robotsDisallowed";
    }

    // CloudFoundry
    @RequestMapping(value = "/loaderio-17880d673a082f6f7006736ce03099a0.txt", method = RequestMethod.GET)
    public String getCloudFoundryVerification() {
        return "loaderio-17880d673a082f6f7006736ce03099a0";
    }

    // Heroku
    @RequestMapping(value = "/loaderio-2a4ac599cd85805f9f1f46b178c11bfa.txt", method = RequestMethod.GET)
    public String getHerokuVerification() {
        return "loaderio-2a4ac599cd85805f9f1f46b178c11bfa";
    }
}
