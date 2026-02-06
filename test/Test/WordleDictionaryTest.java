import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class WordleDictionaryTest {

    @Test
    void contains_invalidWord_returnsFalse() {
        Set<String> words = new HashSet<>(List.of("кошка", "мышка"));
        WordleDictionary dict = new WordleDictionary(words);
        assertFalse(dict.contains("слон"));
    }

    @Test
    void getRandomWord_returnsWordFromSet() {
        Set<String> words = new HashSet<>(List.of("кошка", "мышка"));
        WordleDictionary dict = new WordleDictionary(words);
        String word = dict.getRandomWord();
        assertTrue(words.contains(word));
    }

    @Test
    void constructor_rejectsNon5LetterWords() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WordleDictionary(new HashSet<>(List.of("кот", "кошка")));
        });
    }
}