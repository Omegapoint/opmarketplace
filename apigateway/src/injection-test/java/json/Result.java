package json;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private final List<String> failures;

    public Result(int numTestCases) {
        this.numTestCases = notNull(numTestCases);
        this.failures = new ArrayList<>();
        numBlocked = 0;
        blocked = numBlocked + "/" + numTestCases;
    }

    public void registerBlock(){
        isTrue(numBlocked != numTestCases);
        numBlocked++;
        blocked = numBlocked + "/" + numTestCases;
    }

    public void registerFailiure(String text){
        failures.add(text);
    }

    public String getBlocked() {
        return blocked;
    }

    public List<String> getFailures() {
        return failures;
    }
}
