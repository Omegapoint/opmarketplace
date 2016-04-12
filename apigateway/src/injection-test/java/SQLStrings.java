import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SQLStrings {
    private List<String> values;

    public SQLStrings(File file) throws IOException {
        this.values = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null){
            values.add(line);
        }
    }

    public List<String> values(){
        return this.values;
    }
}
