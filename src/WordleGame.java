import java.util.*;

public class WordleGame {
    private final WordleDictionary dictionary;
    private final String secretWord;
    private int attemptsLeft;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.secretWord = dictionary.getRandomWord();
        this.attemptsLeft = 6;
    }

    public boolean isWon() {
        return !guesses.isEmpty() && guesses.get(guesses.size() - 1).equals(secretWord);
    }

    public boolean isLost() {
        return attemptsLeft <= 0 && !isWon();
    }

    public String makeGuess(String guess) throws WordNotFoundInDictionary {
        guess = normalize(guess);
        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionary("Слова нет в словаре!");
        }

        guesses.add(guess);
        attemptsLeft--;

        String hint = generateHint(guess);
        hints.add(hint);
        return hint;
    }

    public List<String> getSuggestions() {
        if (guesses.isEmpty()) {
            return Collections.singletonList(dictionary.getRandomWord());
        }
        return dictionary.getSuggestions(guesses.get(guesses.size() - 1), hints.get(hints.size() - 1));
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

    public int getAttemptsLeft() { return attemptsLeft; }
    public String getSecretWord() { return secretWord; }
    public List<String> getGuesses() { return guesses; }
    public List<String> getHints() { return hints; }
}
