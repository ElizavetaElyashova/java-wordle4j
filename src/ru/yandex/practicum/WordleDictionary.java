package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> words;
    private PrintWriter log;


    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = words;
        this.log = log;
    }

    public static boolean checkLength(String word) {
        return word.length() == 5;
    }

    public static String normalize(String word) {
        return word.toLowerCase().replace("ё", "е");
    }

    public int size() {
        return words.size();
    }

    public String get(int i) {
        return words.get(i);
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public List<String> getWords() {
        return words;
    }
}
