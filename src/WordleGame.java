import exception.WordNotFoundInDictionary;

import java.io.IOException;
import java.util.*;

public class WordleGame {
    private final WordleDictionary dictionary;
    private final String secretWord;
    private int attemptsLeft;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();
    private final Set<String> usedHints = new HashSet<>();
    int hintsUsed = 0;
    private boolean won = false;
    private boolean lost = false;

    public WordleGame(WordleDictionary dictionary) throws IOException {
        this.dictionary = dictionary;
        this.secretWord = dictionary.getRandomWord();
        if (secretWord.length() != 5) {
            throw new IllegalStateException("Секретное слово должно быть из 5 букв: " + secretWord);
        }
        this.attemptsLeft = 6;
    }


    public boolean isWon() { return won; }
    public boolean isLost() { return lost; }

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
        }
        attemptsLeft--;
        if (attemptsLeft <= 0 && !won) {
            lost = true;
        }

        return hint;
    }

    private String normalize(String word) {
        return word.trim().toLowerCase().replace('ё', 'е');
    }

    private String generateHint(String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < 5; i++) {
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
        if (hintsUsed >= 5) {
            return "Нет доступных подсказок!";
        }
        hintsUsed++;
        List<String> candidates = dictionary.getHintWords(secretWord, usedHints);
        if (candidates.isEmpty()) {
            return "Нет доступных подсказок!";
        }

        String hint = candidates.get(new Random().nextInt(candidates.size()));
        usedHints.add(hint);
        hintsUsed++;
        return hint;
    }

    public int getAttemptsLeft() { return attemptsLeft; }
    public String getSecretWord() { return secretWord; }
}
