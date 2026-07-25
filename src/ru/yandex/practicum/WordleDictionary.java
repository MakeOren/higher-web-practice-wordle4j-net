package ru.yandex.practicum;


import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> dictionary;
    private static final int WORD_LENGTH = 5;

    public WordleDictionary(List<String> words) {
        this.dictionary = words
                .stream()
                .map(word -> word.trim().replace("ё", "е").toLowerCase(Locale.ROOT))
                .filter(word -> word.length() == WORD_LENGTH)
                .collect(Collectors.toList());

        if (dictionary.isEmpty()) {
            throw new EmptyDictionaryException("После фильтрации словарь пуст");
        }
    }
}
