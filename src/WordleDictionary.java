import java.util.*;

public class WordleDictionary {
    final Set<String> words;

    public WordleDictionary(Set<String> words) {
        this.words = new HashSet<>();
        for (String word : words) {
            if (word.trim().length() != 5) {
                throw new IllegalArgumentException("Слово должно быть из 5 букв: " + word);
            }
            this.words.add(word.trim().toLowerCase());
        }
    }


    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        List<String> wordList = new ArrayList<>(words);
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    public List<String> getHintWords(String secretWord, Set<String> usedHints) {
        List<String> candidates = new ArrayList<>();
        for (String word : words) {
            if (!word.equals(secretWord) && !usedHints.contains(word)) {
                for (char c : secretWord.toCharArray()) {
                    if (word.indexOf(c) != -1) {
                        candidates.add(word);
                        break;
                    }
                }
            }
        }
        return candidates;
    }
}
