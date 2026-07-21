package ru.yandex.practicum;

import java.util.List;

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

    public static void main(String[] args) {


        List<String> lines = WordleDictionaryLoader.load("words_ru.txt");

        for (String line : lines) {
            System.out.println(line);
        }

        System.out.println(lines.get(3));
    }

}
