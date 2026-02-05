import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("game.log"))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.load("dictionary.txt");
            WordleGame game = new WordleGame(dictionary);

            log.println("Игра начата. Загадано слово: " + game.getSecretWord());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Добро пожаловать в Wordle! У вас 6 попыток.");

            while (!game.isWon() && !game.isLost()) {
                System.out.print("Введите слово (или Enter для подсказки): ");
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    List<String> suggestions = game.getSuggestions();
                    if (!suggestions.isEmpty()) {
                        System.out.println("Подсказка: " + suggestions.get(0));
                        log.println("Подсказка выдана: " + suggestions.get(0));
                    } else {
                        System.out.println("Нет подходящих слов.");
                    }
                    continue;
                }

                String hint = game.makeGuess(input);
                System.out.println(input);
                System.out.println(hint);
                log.println("Попытка: " + input + " → " + hint);
            }

            if (game.isWon()) {
                System.out.println("Поздравляю! Вы угадали слово: " + game.getSecretWord());
                log.println("Игрок выиграл.");
            } else {
                System.out.println("Вы проиграли. Загаданное слово: " + game.getSecretWord());
                log.println("Игрок проиграл. Слово: " + game.getSecretWord());
            }

        } catch (IOException e) {
            System.err.println("Ошибка работы с файлами: " + e.getMessage());
        }
    }
}
