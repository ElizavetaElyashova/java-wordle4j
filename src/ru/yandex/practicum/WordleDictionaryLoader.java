package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load() {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (WordleDictionary.checkLength(word)) {
                    words.add(WordleDictionary.normalize(word));
                }
            }
        } catch (FileNotFoundException e) {
            log.println(e.getMessage());
        } catch (IOException e) {
            log.println(e.getMessage());
        }
        return new WordleDictionary(words, log);
    }
}
