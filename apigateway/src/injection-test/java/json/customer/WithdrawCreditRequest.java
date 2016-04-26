package json.customer;

import com.fasterxml.jackson.core.JsonParseException;
import json.Requests;
import json.Result;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

public class WithdrawCreditRequest {

    public final Result email;
    public final Result credit;

    public WithdrawCreditRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("WithdrawCreditRequest Validation Started");
        this.email = email(values, mockMvc);
        this.credit = credit(values, mockMvc);
        System.out.println("WithdrawCreditRequest Validation Ended");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try{
                MvcResult mvcResult = Requests.withdrawCredit(s, "0", mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (AuthenticationCredentialsNotFoundException e){
                res.registerBlock();
            }
        }
        return res;
    }

    private Result credit(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.withdrawCredit("valid@valid.com", s, mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (MethodArgumentTypeMismatchException | IllegalArgumentException e){
                res.registerBlock();
            }
        }
        return res;
    }
}
