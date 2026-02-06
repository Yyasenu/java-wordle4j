import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

class WordleDictionaryLoaderTest {

    @Test
    void loadFromResources_fileNotFound_throwsException() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        assertThrows(FileNotFoundException.class, () -> {
            loader.loadFromResources("nonexistent.txt");
        });
    }

    @Test
    void normalize_convertsToLowercaseAndReplacesYo() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        assertEquals("елка", loader.normalize("Ёлка"));
        assertEquals("мел", loader.normalize("Мёл"));
    }
}
