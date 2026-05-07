package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    static WordleGame game;
    static PrintWriter log;
    static WordleDictionaryLoader loader;
    static WordleDictionary dictionary;

    @BeforeAll
    static void BeforeAll() {
        log = new PrintWriter(System.out);
        loader = new WordleDictionaryLoader(log);
        dictionary = loader.load();
    }

    @BeforeEach
    void BeforeEach() {
        game = new WordleGame(dictionary, log);
    }

    @Test
    void testCheckGuessAllWrong() {
        game.setAnswer("баран");
       assertEquals("-----", game.checkGuess("потоп"));
    }

    @Test
    void testCheckGuessAllRight() {
        game.setAnswer("кошка");
        assertEquals("+++++", game.checkGuess("кошка"));
    }

    @Test
    void testCheckGuessPartlyRight() {
        game.setAnswer("банан");
        assertEquals("-++-^", game.checkGuess("манка"));
    }

    @Test
    void testRemoveWrongWordsRightLettersAndWrongLetters() {
        game.setRightLetters(Set.of('а', 'б'));
        game.setWrongLetters(Set.of('о', 'ш'));
        game.setComputerGuess(new ArrayList<>(List.of("кошка", "банан", "каска")));
        game.removeWrongWords();
        String[] test = new String[] {"банан"};
        assertArrayEquals(test, game.getComputerGuess().toArray());
    }

    @Test
    void testRemoveWrongWordsByWord() {
        char[] word = new char[] {'-', 'а', '-', 'к', 'а'};
        game.setWord(word);
        game.setComputerGuess(new ArrayList<>(List.of("кошка", "банан", "каска", "шапка", "балда")));
        String[] test = new String[] {"каска", "шапка"};
        game.removeWrongWords();
        assertArrayEquals(test, game.getComputerGuess().toArray());
    }


}
