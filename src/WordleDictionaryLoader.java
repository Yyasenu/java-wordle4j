import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordleDictionaryLoader {

    public WordleDictionary loadFromResources(String resourceName) throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource(resourceName);
        if (resourceUrl == null) {
            throw new FileNotFoundException("Ресурс не найден: " + resourceName);
        }

        Set<String> words = new HashSet<>();
        try (InputStream inputStream = resourceUrl.openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String normalized = normalize(line.trim());
                if (normalized.length() == 5) {
                    words.add(normalized);
                }
            }
        }

        if (words.isEmpty()) {
            throw new IllegalArgumentException("Словарь пуст! Проверьте файл: " + resourceName);
        }
        return new WordleDictionary(words);
    }

    String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }
}
