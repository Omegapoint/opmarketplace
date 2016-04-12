package json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonExtractor {
    public final String reason;

    @JsonCreator
    public ReasonExtractor(@JsonProperty("reason") String reason) {
        this.reason = reason;
    }

    public boolean isValidationFailure(){
        return reason.contains("Illegal Format:");
    }
}
