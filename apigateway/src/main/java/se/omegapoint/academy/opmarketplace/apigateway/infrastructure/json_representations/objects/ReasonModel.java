package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ReasonModel {

    private String reason;

    public ReasonModel(String reason){
        this.reason = notNull(reason);
    }
 
    public ReasonModel(){}

    public String getReason() {
        return reason;
    }
}
