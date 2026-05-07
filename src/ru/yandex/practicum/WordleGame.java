package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> input;
    private boolean isWin;
    private PrintWriter log;
    private List<String> computerGuess;
    private Set<Character> wrongLetters;
    private Set<Character> rightLetters;
    private char[] word;


    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        answer = guessWord();
        steps = 6;
        this.dictionary = dictionary;
        input = new ArrayList<>();
        isWin = false;
        this.log = log;
        computerGuess = new ArrayList<>(dictionary.getWords());
        wrongLetters = new HashSet<>();
        rightLetters = new HashSet<>();
        word = new char[]{'-', '-', '-', '-', '-'};
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public List<String> getComputerGuess() {
        return computerGuess;
    }

    public void setComputerGuess(List<String> computerGuess) {
        this.computerGuess = computerGuess;
    }

    public void setWrongLetters(Set<Character> wrongLetters) {
        this.wrongLetters = wrongLetters;
    }

    public void setRightLetters(Set<Character> rightLetters) {
        this.rightLetters = rightLetters;
    }

    public void setWord(char[] word) {
        this.word = word;
    }


    public String guessWord() {
        int rnd = (int) ((dictionary.size() - 1) * Math.random());
        return dictionary.get(rnd);
    }


    public String checkGuess(String guess) {
        input.add(guess);
        StringBuilder builder = new StringBuilder();
        builder.repeat("-", answer.length());
        Map<Character, Integer> frequency = new HashMap<>();
        for (Character ch : answer.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < answer.length(); i++) {
            char ch = guess.charAt(i);
            if (ch == answer.charAt(i)) {
                builder.replace(i, i + 1, "+");
                frequency.put(ch, frequency.get(ch) - 1);
                rightLetters.add(ch);
                word[i] = ch;
            }
        }
        for (int i = 0; i < answer.length(); i++) {
            char ch = guess.charAt(i);
            if (answer.contains(String.valueOf(ch))) {
                if (frequency.get(ch) != 0 && builder.charAt(i) != '+') {
                    builder.replace(i, i + 1, "^");
                    frequency.put(ch, frequency.get(ch) - 1);
                    rightLetters.add(ch);
                }
            } else {
                wrongLetters.add(ch);
            }
        }
        return builder.toString();
    }

    public void removeWrongWords() {
        List<String> computerGuessCopy = new ArrayList<>(computerGuess);
        outer:
        for (String word : computerGuessCopy) {
            if (input.contains(word)) {
                computerGuess.remove(word);
            } else {
                for (Character ch : wrongLetters) {
                    if (word.contains(String.valueOf(ch))) {
                        computerGuess.remove(word);
                        continue outer;
                    }
                }
                for (Character ch : rightLetters) {
                    if (!word.contains(String.valueOf(ch))) {
                        computerGuess.remove(word);
                        continue outer;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (this.word[i] != '-') {
                        if (this.word[i] != word.charAt(i)) {
                            computerGuess.remove(word);
                            continue outer;
                        }
                    }
                }
            }
        }
    }

    public String giveGuess() {
        if (steps < 6) {
            removeWrongWords();
        }
        int rnd = (int) ((computerGuess.size() - 1) * Math.random());
        return computerGuess.get(rnd);
    }
}
