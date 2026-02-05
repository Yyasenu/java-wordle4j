import exception.WordNotFoundInDictionary;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("game.log"))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.loadFromResources("words_ru.txt");
            WordleGame game = new WordleGame(dictionary);

            log.println("Игра начата. Загадано слово: " + game.getSecretWord());

            Scanner scanner = new Scanner(System.in);

            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("У вас 6 попыток, чтобы угадать слово из 5 букв.");
            System.out.println("Нажмите Enter для подсказки (доступно 2 подсказки за игру).");
            System.out.println("- «+» — буква на своём месте");
            System.out.println("- «^» — буква есть, но не на месте");
            System.out.println("- «-» — буквы нет в слове");
            System.out.println("=================================");


            while (!game.isWon() && !game.isLost()) {
                List<String> history = game.getHistory();
                if (!history.isEmpty()) {
                    for (String line : history) {
                        System.out.println(line);
                    }
                    System.out.println("---------------------------------");
                }

                System.out.print("Ваш ход (слово или Enter для подсказки): ");
                String input = scanner.nextLine().trim();
                boolean processInput = true;

                if (input.isEmpty()) {
                    String hint = game.getRandomHint();
                    System.out.println("> Подсказка: " + hint);
                    log.println("Подсказка: " + hint);
                    processInput = false;
                }
                if (processInput) {
                    if (input.length() != 5) {
                        System.out.println("Ошибка: слово должно состоять из 5 букв!");
                        log.println("Ошибка ввода: '" + input + "' — не 5 букв");
                        processInput = false;
                    } else if (!input.matches("[а-яё]+")) {
                        System.out.println("Ошибка: разрешены только русские буквы!");
                        log.println("Ошибка ввода: '" + input + "' — недопустимые символы");
                        processInput = false;
                    }
                }
                if (processInput) {
                    try {
                        String feedback = game.makeGuess(input);
                        System.out.println("> " + input);
                        System.out.println("> " + feedback);
                        log.println("Попытка: " + input + " → " + feedback);
                    } catch (WordNotFoundInDictionary e) {
                        System.out.println("Ошибка: " + e.getMessage());
                        log.println("Ошибка ввода: " + input + " — " + e.getMessage());
                    }
                }
            }
            System.out.println("=================================");
            List<String> finalHistory = game.getHistory();
            for (String line : finalHistory) {
                System.out.println(line);
            }
            System.out.println("# загаданное слово: " + game.getSecretWord());
            log.println("Загаданное слово: " + game.getSecretWord());

            if (game.isWon()) {
                System.out.println("Поздравляем! Вы угадали слово!");
                log.println("Игрок выиграл.");
            } else {
                System.out.println("Вы проиграли. Попробуйте ещё раз!");
                log.println("Игрок проиграл.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: не найден файл словаря: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка словаря: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом лога: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
