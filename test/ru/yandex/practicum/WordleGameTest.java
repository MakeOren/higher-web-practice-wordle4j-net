package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.yandex.practicum.game.WordleDictionary;
import ru.yandex.practicum.game.WordleGame;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {
    WordleDictionary wordleDictionary;
    WordleGame wordleGame;

    @BeforeEach
    void setUp() {
        List<String> dictionary = new ArrayList<>();
        dictionary.add("взбег");
        wordleDictionary = new WordleDictionary(dictionary);
        wordleGame = new WordleGame(wordleDictionary);

    }

    @Test
    void getSuggestion_doesNotExcludeLetterAlreadyFixedByPlusHint() {
        wordleGame.makeMove("шевер");
        assertDoesNotThrow(() -> wordleGame.getSuggestion());
    }

    @Test
    void getSuggestion_doesNotExcludeLetterAlreadyIncludedByCaretHint() {
        wordleGame.makeMove("шевре");
        assertDoesNotThrow(() -> wordleGame.getSuggestion());
    }

    @ParameterizedTest
    @CsvSource({
            "шевер, --^+-",
            "гонец, ^--+-",
            "олень, --^--",
            "взбег, +++++",
            "шевре, -^^--"
    })
    void checkGuess_returnsCorrectHint(String guess, String expectedHint) {
        String actualHint = wordleGame.makeMove(guess);
        assertEquals(expectedHint, actualHint);
    }

    @Test
    void makeMove_winsGame_onCorrectGuess() {
        String correctGuess = "взбег";
        wordleGame.makeMove(correctGuess);
        assertTrue(wordleGame.isWin());
        assertTrue(wordleGame.isGameOver());
        assertEquals(5, wordleGame.getSteps());
    }

    @Test
    void makeMove_loseGame_onInCorrectGuess() {
        wordleGame.makeMove("низка");
        wordleGame.makeMove("адити");
        wordleGame.makeMove("аванс");
        wordleGame.makeMove("нищие");
        wordleGame.makeMove("олень");
        wordleGame.makeMove("шевер");
        assertFalse(wordleGame.isWin());
        assertTrue(wordleGame.isGameOver());
        assertEquals(0, wordleGame.getSteps());
    }

    @Test
    void makeMove_midGame_isNotGameOver() {
        wordleGame.makeMove("низка");
        wordleGame.makeMove("адити");
        wordleGame.makeMove("аванс");
        assertFalse(wordleGame.isWin());
        assertFalse(wordleGame.isGameOver());
        assertEquals(3, wordleGame.getSteps());
    }

    @Test
    void getSuggestion_returnsWord_whenHistoryIsEmpty() {
        assertDoesNotThrow(() -> wordleGame.getSuggestion());
    }

    @Test
    void isUsedHints_becomesTrue_afterGetSuggestionCalled() {
        assertFalse(wordleGame.isUsedHints());
        wordleGame.getSuggestion();
        assertTrue(wordleGame.isUsedHints());
    }


}
