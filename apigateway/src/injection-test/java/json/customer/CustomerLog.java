package json.customer;

public class CustomerLog {
    public final CreateAccountRequest createAccountRequest;
    public final ChangeUserRequest changeUserRequest;
    public final DepositCreditRequest depositCreditRequest;
    public final WithdrawCreditRequest withdrawCreditRequest;
    public final GetAccountRequest getAccountRequest;
    public final DeleteAccountRequest deleteAccountRequest;

    public CustomerLog(CreateAccountRequest createAccountRequest,
                       ChangeUserRequest changeUserRequest,
                       DepositCreditRequest depositCreditRequest,
                       WithdrawCreditRequest withdrawCreditRequest,
                       GetAccountRequest getAccountRequestRequest,
                       DeleteAccountRequest deleteAccountRequest) {
        this.createAccountRequest = createAccountRequest;
        this.changeUserRequest = changeUserRequest;
        this.depositCreditRequest = depositCreditRequest;
        this.withdrawCreditRequest = withdrawCreditRequest;
        this.getAccountRequest = getAccountRequestRequest;
        this.deleteAccountRequest = deleteAccountRequest;
    }
}
