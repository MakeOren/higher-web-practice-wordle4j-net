package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.EmptyDictionaryException;
import ru.yandex.practicum.game.WordleDictionary;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordleDictionaryTest {

    WordleDictionary wordleDictionary;

    @Test
    void constructor_normalizesWords_caseAndYo() {
        List<String> words = new ArrayList<>();
        words.add("Ёршик");
        words.add("Лётка");

        wordleDictionary = new WordleDictionary(words);

        assertTrue(wordleDictionary.containsWord("летка"));
        assertTrue(wordleDictionary.containsWord("ершик"));

    }

    @Test
    void constructor_ifDictionaryIsEmpty_throwEmptyDictionaryException() {
        List<String> words = new ArrayList<>();
        words.add("Ё");
        words.add("Лёткаa");
        words.add("Ёрш");
        words.add("Лётк");

        assertThrows(EmptyDictionaryException.class, () -> {
            wordleDictionary = new WordleDictionary(words);
        });
    }
}
