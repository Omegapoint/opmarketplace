package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TitleDTO implements DTO, Serializer, Deserializer<Title> {

    public final String text;

    public TitleDTO(Title title){
        this.text = notNull(title).text();
    }

    @JsonCreator
    public TitleDTO(@JsonProperty("text") String text) {
        this.text = notNull(text);
    }

    @Override
    public Title domainObject() {
        return new Title(this.text);
    }
}
