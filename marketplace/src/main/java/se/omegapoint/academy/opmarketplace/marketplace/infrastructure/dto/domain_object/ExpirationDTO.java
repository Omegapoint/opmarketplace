package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Expiration;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpirationDTO implements DTO, Serializer, Deserializer<Expiration> {

    public final Timestamp time;

    public ExpirationDTO(Expiration expiration){
        this.time = notNull(expiration).time();
    }

    @JsonCreator
    public ExpirationDTO(@JsonProperty("time") String time) {
        this.time = Timestamp.valueOf(notNull(time));
    }

    @Override
    public Expiration domainObject() {
        return new Expiration(this.time);
    }
}
