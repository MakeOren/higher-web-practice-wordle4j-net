package ru.yandex.practicum.game;

import ru.yandex.practicum.exception.WordNotFoundDictionaryException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    private static final String PATH = "words_ru.txt";
    private static final String PATH_LOG = "log.txt";
    private static final int STEP_MAX = 6;

   

    public static void main(String[] args) {
        PrintWriter log = null;
        Scanner scanner = new Scanner(System.in);
        try {
            log = new PrintWriter(new BufferedWriter(new FileWriter(PATH_LOG, StandardCharsets.UTF_8)));
            WordleDictionary wordleDictionary = new WordleDictionary(WordleDictionaryLoader.load(PATH));
            WordleGame wordleGame = new WordleGame(wordleDictionary);

            System.out.println("Добро пожаловать в игру!");
            System.out.println("Введите слово размером пять символов:");
            while (true) {
                try {
                    String word = scanner.nextLine();

                    if (word.trim().isEmpty()) {
                        System.out.println("Потенциальный ответ: " + wordleGame.getSuggestion());
                        System.out.println("Введите слово размером пять символов:");
                        continue;
                    }

                    if (!wordleDictionary.containsWord(word)) {
                        throw new WordNotFoundDictionaryException("Слово не найдено в словаре");
                    }

                    String hint = wordleGame.makeMove(word);
                    int step = wordleGame.getSteps();

                    if (wordleGame.isGameOver()) {
                        if (wordleGame.isWin()) {
                            System.out.println("Поздравляем с победой!");
                            System.out.println("Количество использованных попыток: " + (STEP_MAX - step));
                            System.out.println("Отгаданное слово: " + word);
                            break;
                        } else {
                            System.out.println("Вы проиграли :(");
                            System.out.println("Cлово: " + wordleGame.getAnswer());
                            break;
                        }
                    }

                    System.out.println("Вы не угадали");
                    System.out.println("Подсказка: " + hint);
                    System.out.println("Количество оставшихся попыток: " + step);


                } catch (WordNotFoundDictionaryException e) {
                    System.out.println("Вы ввели некорректное слово, повторите попытку!");
                    System.out.println("Введите слово размером пять символов:");
                }

            }

        } catch (Exception e) {
            e.printStackTrace(log);
        } finally {
            if (log != null) {
                log.close();
            }
        }
    }

}
