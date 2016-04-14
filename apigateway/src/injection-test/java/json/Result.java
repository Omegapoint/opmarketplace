package json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Result {

    @JsonIgnore
    private final int numTestCases;
    @JsonIgnore
    private int numBlocked;
    private String blocked;
    private final List<NoBlock> notBlocked;

    public Result(int numTestCases) {
        this.numTestCases = notNull(numTestCases);
        this.notBlocked = new ArrayList<>();
        numBlocked = 0;
        blocked = numBlocked + "/" + numTestCases;
    }

    public void registerResult(MvcResult result, String input) throws IOException {
        if (Requests.isBlock(result)){
            registerBlock();
        } else {
            registerNotBlocked(input, result.getResponse().getContentAsString());
        }
    }

    public void registerBlock(){
        isTrue(numBlocked != numTestCases);
        numBlocked++;
        blocked = numBlocked + "/" + numTestCases;
    }

    private void registerNotBlocked(String text, String response){
        notBlocked.add(new NoBlock(text, response));
    }

    public String getBlocked() {
        return blocked;
    }

    public List<NoBlock> getNotBlocked() {
        return notBlocked;
    }
}
