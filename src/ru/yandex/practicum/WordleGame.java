package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final String answer;

    private int steps;

    private final WordleDictionary dictionary;

    private final List<Attempt> history;

    private boolean guessed;

    private record Attempt(String word, String hint) {

    }

    private record HintContext(Set<Character> excluded, Set<Character> included, Map<Integer,Character> fixed) {

    }

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
        this.guessed = false;
        this.history = new ArrayList<>();
    }

    private String checkGuess(String guess) {
        Map<Character, Integer> count = new HashMap<>();
        StringBuilder guessBuilder =  new StringBuilder(guess);

        for (int i = 0; i < answer.length(); i++) {
            count.put(answer.charAt(i), count.getOrDefault(answer.charAt(i), 0) + 1);

        }

        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == guessBuilder.charAt(i)) {
                guessBuilder.setCharAt(i, '+');
                count.put(answer.charAt(i), count.get(answer.charAt(i)) - 1);
            }
        }

        for (int i = 0; i < guessBuilder.length(); i++) {
            if (count.get(guessBuilder.charAt(i)) == null || count.get(guessBuilder.charAt(i)) == 0) {
                if (guessBuilder.charAt(i) != '+') {
                    guessBuilder.setCharAt(i, '-');
                }

            }
            else if (count.get(guessBuilder.charAt(i)) != null && count.get(guessBuilder.charAt(i)) != 0){
                count.put(guessBuilder.charAt(i), count.get(guessBuilder.charAt(i)) - 1);
                guessBuilder.setCharAt(i, '^');
            }
        }

        return new String(guessBuilder);
    }

    public String makeMove(String guess) {
        String hint = checkGuess(guess);
        Attempt attempt = new Attempt(guess, hint);

        history.add(attempt);
        steps -= 1;

        if (guess.equals(answer)) {
            guessed = true;
        }

        return hint;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isGameOver() {
        return guessed || steps == 0;
    }

    public boolean isWin() {
        return guessed;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSuggestion() {
        Random random = new Random();
        HintContext hintContext = analyzeHistory();
        List<String> words = dictionary.getAllWords();
        List<String> candidates = new ArrayList<>();

        Set<Character> included = hintContext.included;
        Set<Character> excluded = hintContext.excluded;
        Map<Integer, Character> fixed =  hintContext.fixed;

        outerLoop:
        for (String word : words) {
            for (Character character : excluded) {
                if (word.contains(character.toString())) {
                    continue outerLoop;
                }
            }

            for (Character character : included) {
                if (!word.contains(character.toString())) {
                    continue outerLoop;
                }
            }

            for (Map.Entry<Integer, Character> integerCharacterEntry : fixed.entrySet()) {
                if (!(word.contains(integerCharacterEntry.getValue().toString()) && word.indexOf(integerCharacterEntry.getValue().toString()) == integerCharacterEntry.getKey())) {
                    continue outerLoop;
                }
            }

            for (Attempt attempt : history) {
                if (attempt.word.equals(word)) {
                    continue outerLoop;
                }
            }

            candidates.add(word);
        }

        return candidates.get(random.nextInt(candidates.size()));
    }

    private HintContext analyzeHistory() {
        Set<Character> included = new HashSet<>();
        Set<Character> excluded = new HashSet<>();
        Map<Integer, Character> fixed = new HashMap<>();

        for (Attempt attempt : history) {
            String word = attempt.word;
            String hint = attempt.hint;
            for (int i = 0; i < word.length(); i++) {
                if (hint.charAt(i) == '+') {
                    fixed.put(i,word.charAt(i));
                }

                if (hint.charAt(i) == '^') {
                    included .add(word.charAt(i));
                } else if(hint.charAt(i) != '+'){
                    excluded.add(word.charAt(i));
                }
            }
        }

        return new HintContext(excluded, included, fixed);
    }
}
