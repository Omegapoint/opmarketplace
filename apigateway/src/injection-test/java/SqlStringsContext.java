import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlStringsContext {
    private List<String> values;

    public SqlStringsContext() throws IOException {
        File file = new File("src\\injection-test\\resources\\SqlStringsContext.txt");
        this.values = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String xssString = "";
        while(!(line = reader.readLine()).equals("<END>")){
            while (!line.equals("")){
                xssString += line;
                line = reader.readLine();
            }
            values.add(xssString);
            xssString = "";
        }
    }

    public List<String> values(){
        return this.values;
    }
}
