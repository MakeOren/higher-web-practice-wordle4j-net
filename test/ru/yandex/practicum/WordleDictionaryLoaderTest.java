package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.game.WordleDictionaryLoader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleDictionaryLoaderTest {

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Path.of("test_words.txt"), "слово1\nслово2\nслово3", StandardCharsets.UTF_8);
    }

    @Test
    void load_returnsLinesFromFile() {
        List<String> words = WordleDictionaryLoader.load("test_words.txt");

        assertEquals(3, words.size());

        for (int i = 0; i < words.size(); i++) {
            assertEquals("слово" + (i + 1), words.get(i));
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File("test_words.txt");
        file.delete();
    }
}
