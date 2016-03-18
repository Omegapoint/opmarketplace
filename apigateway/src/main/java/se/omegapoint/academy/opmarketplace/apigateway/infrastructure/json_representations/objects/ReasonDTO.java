package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ReasonDTO {

    private String reason;

    public ReasonDTO(String reason){
        this.reason = notNull(reason);
    }
 
    public ReasonDTO(){}

    public String getReason() {
        return reason;
    }
}
