package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static PrintWriter log;

    public static void main(String[] args) {
        try (Writer writer = new FileWriter("log.txt", StandardCharsets.UTF_8)) {
            log = new PrintWriter(writer);
            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
                WordleDictionary dictionary = loader.load();
                WordleGame game = new WordleGame(dictionary, log);
                playGame(game);
                if (game.isWin()) {
                    log.println("Игра выиграна");
                    System.out.println("Это правильный ответ!");
                } else {
                    log.println("Игра проиграна");
                    System.out.println("Вы не угадали, а слово было: " + game.getAnswer());
                }
            } catch (Exception e) {
                log.println("Ошибка: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playGame(WordleGame game) throws WordNotFound, IncorrectWordLength {
        Scanner scan = new Scanner(System.in);
        while (game.getSteps() > 0) {
            try {
                System.out.println("Введите слово: ");
                String guess = scan.nextLine();
                if (!game.checkLanguage(guess)) {
                    throw new IncorrectInput("Используйте только русские буквы.\n");
                }
                if (guess.isEmpty()) {
                    guess = game.giveGuess();
                    log.println("Вариант компьютера: " + guess);
                    System.out.println(guess);
                } else {
                    guess = WordleDictionary.normalize(guess);
                    log.println("Вариант пользователя: " + guess);
                }
                if (guess.equals(game.getAnswer())) {
                    System.out.println("+++++");
                    game.setSteps(game.getSteps() - 1);
                    game.setWin(true);
                    break;
                }
                if (WordleDictionary.checkLength(guess)) {
                    if (game.getDictionary().contains(guess)) {
                        String checkGuess = game.checkGuess(guess);
                        game.setSteps(game.getSteps() - 1);
                        System.out.println(checkGuess);
                        System.out.println("Оставшиеся попытки: " + game.getSteps() + '\n');
                        log.println("Подсказка: " + checkGuess);
                        log.println("Оставшиеся попытки: " + game.getSteps());
                    } else {
                        throw new WordNotFound("Слово не найдено.\n");
                    }
                } else {
                    throw new IncorrectWordLength("Неверная длина слова.\n");
                }

            } catch (GameException e) {
                log.println(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

}
