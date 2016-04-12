package json;

public class AccountLog {
    public final CreateAccountRequest createAccountRequest;
    public final ChangeUserRequest changeUserRequest;
    public final AddCreditRequest addCreditRequest;
    public final GetAccountRequest getAccountRequest;
    public final DeleteAccountRequest deleteAccountRequest;

    public AccountLog(CreateAccountRequest createAccountRequest,
                      ChangeUserRequest changeUserRequest,
                      AddCreditRequest addCreditRequest,
                      GetAccountRequest getAccountRequestRequest,
                      DeleteAccountRequest deleteAccountRequest) {
        this.createAccountRequest = createAccountRequest;
        this.changeUserRequest = changeUserRequest;
        this.addCreditRequest = addCreditRequest;
        this.getAccountRequest = getAccountRequestRequest;
        this.deleteAccountRequest = deleteAccountRequest;
    }
}
