package Task1;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashSet;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HangmanGame game = new HangmanGame();

        System.out.println("Игра 'Виселица'. Попробуйте угадать слово по буквам. Все слова на английском в верхнем регистре,\nнапример, PROGRAMMING");
        System.out.println("У вас " + game.getLives() + " жизней. Удачи!");

        while (!game.isWon() && !game.isLost()) {
            System.out.println();
            System.out.println("Загаданное слово: " + game.getMaskedWord());
            System.out.println("Использованные буквы: " + game.getUsedLettersString());
            System.out.println("Осталось жизней: " + game.getLives());
            game.printHangman();

            System.out.print("Введите одну букву: ");
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Пустой ввод. Попробуйте снова.");
                continue;
            }

            char c = Character.toUpperCase(line.charAt(0));
            if (!Character.isLetter(c)) {
                System.out.println("Нужно ввести букву (A-Z или А-Я). Попробуйте снова.");
                continue;
            }

            if (game.isAlreadyUsed(c)) {
                System.out.println("Эта буква уже была. Введите другую.");
                continue;
            }

            boolean correct = game.guessLetter(c);
            if (correct) {
                System.out.println("Верно! Буква есть в слове.");
            } else {
                System.out.println("Ошибка. Буквы в слове нет.");
            }
        }

        System.out.println();
        if (game.isWon()) {
            System.out.println("Поздравляю! Вы отгадали слово: " + game.getWord());
        } else {
            game.printHangman();
            System.out.println("К сожалению, вы проиграли. Было загадано слово: " + game.getWord());
        }

        in.close();
    }
}

class HangmanGame {
    private static final String[] WORDS = {
            "COMPUTER", "PROGRAMMING", "ALGORITHM", "DEVELOPER", "JAVA",
            "VARIABLE", "FUNCTION", "COMPILER", "DEBUGGER", "ENGINEER"
    };

    // ASCII-рисунки виселицы по количеству оставшихся жизней (индекс = lives)
    private static final String[] HANGMAN = new String[] {
            // 0 жизней — финальная стадия
            "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========",
            // 1
            "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
            // 2
            "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
            // 3
            "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
            // 4
            "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
            // 5
            "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
            // 6 — начальная пустая вешалка
            "  +---+\n  |   |\n      |\n      |\n      |\n      |\n========="
    };

    private final String word;
    private final char[] masked;
    private final Set<Character> usedLetters = new LinkedHashSet<>();
    private int lives = 6; // стандартно 6 попыток

    public HangmanGame() {
        Random rnd = new Random();
        word = WORDS[rnd.nextInt(WORDS.length)];
        masked = new char[word.length()];
        for (int i = 0; i < masked.length; i++) {
            if (Character.isLetter(word.charAt(i))) masked[i] = '_';
            else masked[i] = word.charAt(i);
        }
    }

    public boolean guessLetter(char ch) {
        usedLetters.add(ch);
        boolean found = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                masked[i] = ch;
                found = true;
            }
        }
        if (!found) {
            lives--;
        }
        return found;
    }

    public boolean isAlreadyUsed(char ch) {
        return usedLetters.contains(ch);
    }

    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < masked.length; i++) {
            sb.append(masked[i]);
            if (i < masked.length - 1) sb.append(' ');
        }
        return sb.toString();
    }

    public String getUsedLettersString() {
        if (usedLetters.isEmpty()) return "(нет)";
        StringBuilder sb = new StringBuilder();
        for (char c : usedLetters) {
            sb.append(c).append(' ');
        }
        return sb.toString().trim();
    }

    public boolean isWon() {
        for (char c : masked) {
            if (c == '_') return false;
        }
        return true;
    }

    public boolean isLost() {
        return lives <= 0;
    }

    public String getWord() {
        return word;
    }

    public int getLives() {
        return lives;
    }

    public void printHangman() {
        int idx = Math.max(0, Math.min(lives, HANGMAN.length - 1));
        System.out.println(HANGMAN[idx]);
    }
}
