package domainInjection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dictionary {
    public static List<String> getWordList() throws IOException {
        return Files.lines(Paths.get("src\\injection-test\\resources\\dictionary.txt")).collect(Collectors.toList());
    }
}
