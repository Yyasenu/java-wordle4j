import exception.WordNotFoundInDictionary;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class WordleGameTest {

    private WordleDictionary createTestDictionary() {
        return new WordleDictionary(new HashSet<>(List.of("кошка", "мышка", "ложка")));
    }

    @Test
    void makeGuess_wordNotInDictionary_throwsException() throws Exception {
        WordleGame game = new WordleGame(createTestDictionary());
        assertThrows(WordNotFoundInDictionary.class, () -> {
            game.makeGuess("слон");
        });
    }

    @Test
    void getRandomHint_noRepeats() throws Exception {
        WordleGame game = new WordleGame(createTestDictionary());
        String hint1 = game.getRandomHint();
        String hint2 = game.getRandomHint();
        assertNotEquals(hint1, hint2); // подсказки не повторяются
    }

    @Test
    void getRandomHint_limitedUses() throws Exception {
        WordleGame game = new WordleGame(createTestDictionary());
        game.hintsUsed = 5; // максимум подсказок
        assertEquals("Нет доступных подсказок!", game.getRandomHint());
    }
}
