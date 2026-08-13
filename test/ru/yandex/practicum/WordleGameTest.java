package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.game.WordleDictionary;
import ru.yandex.practicum.game.WordleGame;

import java.util.ArrayList;
import java.util.List;

class WordleGameTest {
    WordleDictionary wordleDictionary;
    WordleGame wordleGame;

    @BeforeEach
    void setUp() {
        List<String> dictionary = new ArrayList<>();
        //dictionary.add("уступ");
        dictionary.add("взбег");
        //dictionary.add("панна");
        wordleDictionary = new WordleDictionary(dictionary);
        wordleGame = new WordleGame(wordleDictionary);

    }

    @Test
    void should() {
        for (int i = 0; i < 1; i++) {
            wordleGame.makeMove("шевер");
            System.out.println(wordleGame.getSuggestion());
        }
    }
}
