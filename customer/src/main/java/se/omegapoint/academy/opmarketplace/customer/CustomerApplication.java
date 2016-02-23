package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.AccountEventStore;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CustomerApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
