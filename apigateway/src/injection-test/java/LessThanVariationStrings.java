import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessThanVariationStrings {
    private List<String> values;

    public LessThanVariationStrings() throws IOException {
        File file = new File("src\\injection-test\\resources\\LessThanVariations.txt");
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
