import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordleDictionaryLoader {
    public WordleDictionary load(String filePath) throws IOException {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String normalized = normalize(line.trim());
                if (normalized.length() == 5) {
                    words.add(normalized);
                }
            }
        }
        if (words.isEmpty()) {
            throw new IllegalArgumentException("Словарь пуст!");
        }
        return new WordleDictionary(words);
    }

    private String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }
}
