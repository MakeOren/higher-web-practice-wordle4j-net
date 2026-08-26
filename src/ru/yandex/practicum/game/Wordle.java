package ru.yandex.practicum.game;

import ru.yandex.practicum.exception.ApiException;
import ru.yandex.practicum.exception.NicknameNullException;
import ru.yandex.practicum.exception.StatisticsSubmissionFailedException;
import ru.yandex.practicum.exception.WordNotFoundDictionaryException;
import ru.yandex.practicum.game.model.LeaderboardEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
    private static PrintWriter log;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            log = new PrintWriter(new BufferedWriter(new FileWriter(PATH_LOG, StandardCharsets.UTF_8)));
            WordleDictionary wordleDictionary = new WordleDictionary(WordleDictionaryLoader.load(PATH));
            WordleGame wordleGame = new WordleGame(wordleDictionary);
            WordleStatisticsClient wordleStatisticsClient = new WordleStatisticsClient();

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
                    int usedAttempts = STEP_MAX - step;
                    boolean usedHints = wordleGame.isUsedHints();

                    if (wordleGame.isGameOver()) {
                        if (wordleGame.isWin()) {
                            System.out.println("Поздравляем с победой!");
                            System.out.println("Количество использованных попыток: " + usedAttempts);
                            System.out.println("Отгаданное слово: " + word);
                            submitStatistics(scanner, wordleStatisticsClient, usedAttempts, usedHints);
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

    private static void submitStatistics(Scanner scanner, WordleStatisticsClient wordleStatisticsClient, int usedAttempts, boolean usedHints) {
        while (true) {
            System.out.println("Введите свой игровой псевдоним, чтобы посмотреть статистику или нажмите enter для выхода из игры");
            System.out.println("Псевдоним может состоять из русских или английских букв, цифр, а также символов: ., _, -.");
            String nickname = scanner.nextLine();

            if (nickname.trim().isEmpty()) {
                System.out.println("Игра завершена!");
                break;
            }

            if (!nickname.matches("[а-яА-ЯёЁa-zA-Z0-9._-]{1,50}")) {
                System.out.println("Псевдоним содержит недопустимые символы или превышает 50 символов");
                continue;
            }

            try {
                wordleStatisticsClient.sendResult(nickname, usedAttempts, usedHints);
                List<LeaderboardEntry> leaderboardEntries =  wordleStatisticsClient.getStatistics(nickname);

                for (int i = 0; i < leaderboardEntries.size(); i++) {
                    LeaderboardEntry leaderboardEntry;
                    if (i < 10) {
                        leaderboardEntry = leaderboardEntries.get(i);
                        System.out.printf("%d %s количество побед: %d%n", i + 1, leaderboardEntry.getNickname(), leaderboardEntry.getWins());
                    }

                    if (i == 10) {
                       leaderboardEntry = leaderboardEntries.get(i);
                        System.out.printf("XX %s количество побед: %d%n", leaderboardEntry.getNickname(), leaderboardEntry.getWins());
                    }
                }
                break;
            } catch (StatisticsSubmissionFailedException | ApiException | NicknameNullException e) {
                System.out.println("Не удалось получить статистику от сервера");
                e.printStackTrace(log);
            } finally {
                break;
            }
        }
    }
}
