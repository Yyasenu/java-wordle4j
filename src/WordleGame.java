import exception.WordNotFoundInDictionary;

import java.io.IOException;
import java.util.*;

public class WordleGame {
    private static final int WORD_LENGTH = 5;
    private static final int MAX_ATTEMPTS = 6;
    private static final int MAX_HINTS = 2;

    private final WordleDictionary dictionary;
    private final String secretWord;
    private int attemptsLeft;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();
    private final Set<String> usedHints = new HashSet<>();
    private int hintsUsed = 0;
    private boolean won = false;

    public WordleGame(WordleDictionary dictionary) throws IOException {
        this.dictionary = dictionary;
        this.secretWord = dictionary.getRandomWord();
        if (secretWord.length() != WORD_LENGTH) {
            throw new IllegalStateException(
                    "Секретное слово должно быть из " + WORD_LENGTH + " букв: " + secretWord
            );
        }
        this.attemptsLeft = MAX_ATTEMPTS;
    }

    public boolean isWon() {
        return won;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public String makeGuess(String guess) throws WordNotFoundInDictionary {
        guess = normalize(guess);

        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionary("Слова нет в словаре!");
        }
        guesses.add(guess);
        String hint = generateHint(guess);
        hints.add(hint);

        if (hint.equals("+++++")) {
            won = true;
        } else {
            attemptsLeft--;
        }
        return hint;
    }

    private String normalize(String word) {
        return word.trim().toLowerCase().replace('ё', 'е');
    }

    private String generateHint(String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < WORD_LENGTH; i++) {
            char g = guess.charAt(i);
            char s = secretWord.charAt(i);
            if (g == s) {
                hint.append('+');
            } else if (secretWord.indexOf(g) != -1) {
                hint.append('^');
            } else {
                hint.append('-');
            }
        }
        return hint.toString();
    }

    public List<String> getHistory() {
        List<String> history = new ArrayList<>();
        for (int i = 0; i < guesses.size(); i++) {
            history.add("> " + guesses.get(i));
            history.add("> " + hints.get(i));
        }
        return history;
    }

    public String getRandomHint() {
        if (hintsUsed >= MAX_HINTS) {
            return "Нет доступных подсказок!";
        }
        hintsUsed++;

        List<String> candidates = dictionary.getHintWords(secretWord, usedHints);
        if (candidates.isEmpty()) {
            return "Нет доступных подсказок!";
        }

        String hint = candidates.get(new Random().nextInt(candidates.size()));
        usedHints.add(hint);
        return hint;
    }
}
