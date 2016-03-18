package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

public class AccountDTO {
    private EmailDTO email;
    private UserDTO user;

    public AccountDTO(){}

    public AccountDTO(EmailDTO email, UserDTO user) {
        this.email = email;
        this.user = user;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public UserDTO getUser() {
        return user;
    }
}
