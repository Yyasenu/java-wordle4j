import java.util.*;

public class WordleDictionary {
    private final Set<String> words;

    public WordleDictionary(Set<String> words) {
        this.words = words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        List<String> wordList = new ArrayList<>(words);
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    public List<String> getSuggestions(String currentGuess, String hint) {
        List<String> suggestions = new ArrayList<>();
        for (String word : words) {
            if (matchesPattern(word, currentGuess, hint)) {
                suggestions.add(word);
            }
        }
        return suggestions;
    }

    private boolean matchesPattern(String word, String guess, String hint) {
        if (guess.isEmpty() || hint.isEmpty()) return true;

        for (int i = 0; i < 5; i++) {
            char h = hint.charAt(i);
            char w = word.charAt(i);
            char g = guess.charAt(i);

            if (h == '+' && w != g) return false;
            if (h == '^' && (w == g || !word.contains(String.valueOf(g)))) return false;
            if (h == '-' && word.contains(String.valueOf(g))) return false;
        }
        return true;
    }
}
