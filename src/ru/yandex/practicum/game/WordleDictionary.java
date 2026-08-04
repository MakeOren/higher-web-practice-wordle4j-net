package ru.yandex.practicum.game;


import ru.yandex.practicum.exception.EmptyDictionaryException;
import ru.yandex.practicum.exception.WordNullException;

import java.util.*;
import java.util.stream.Collectors;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> wordsList;
    // для getRandomWord() — нужен индекс
    private final Set<String> wordsSet;

    private static final int WORD_LENGTH = 5;

    private final Random random;

    public WordleDictionary(List<String> words) {
        List<String> processedWords  = words
                .stream()
                .map(word -> word.trim().replace("ё", "е").toLowerCase(Locale.ROOT))
                .filter(word -> word.length() == WORD_LENGTH)
                .collect(Collectors.toList());

        this.wordsList = new ArrayList<>(processedWords);
        this.wordsSet = new HashSet<>(processedWords);
        this.random = new Random();

        if (wordsList.isEmpty() || wordsSet.isEmpty()) {
            throw new EmptyDictionaryException("После фильтрации словарь пуст");
        }
    }

    public String getRandomWord() {
        return wordsList.get(random.nextInt(wordsList.size()));
    }

    public boolean containsWord(String word) {
        if (word == null) {
            throw new WordNullException("В метод WordleDictionary.containsWord передано null значение");
        }

        if (word.length() != WORD_LENGTH) {
            return false;
        }

        return wordsSet.contains(word);
    }

    public List<String> getAllWords() {
        return new ArrayList<>(wordsList);
    }
}
