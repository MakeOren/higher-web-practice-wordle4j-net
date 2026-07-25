package ru.yandex.practicum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

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

    private static WordleDictionary wordleDictionary;
    private static final String PATH = "words_ru.txt";
    private static PrintWriter log;

    public static void main(String[] args) {
        try (log = new PrintWriter( new BufferedWriter(new FileWriter("log.txt", StandardCharsets.UTF_8)))) {
            wordleDictionary = new WordleDictionary(WordleDictionaryLoader.load(PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
