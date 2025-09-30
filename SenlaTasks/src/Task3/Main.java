package Task3;

import java.util.Scanner;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Выберите длину пароля (8-12): ");
        int length = 0;

        while (true) {
            if (in.hasNextInt()) {
                length = in.nextInt();
                if (length >= 8 && length <= 12) {
                    break;
                } else {
                    System.out.print("Неверная длина. Введите число от 8 до 12: ");
                }
            } else {
                // если ввели не число — читаем и просим снова
                in.next();
                System.out.print("Пожалуйста, введите целое число от 8 до 12: ");
            }
        }

        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generate(length);

        System.out.println("Сгенерированный пароль: " + password);
        in.close();
    }
}


class PasswordGenerator {
    private final SecureRandom rnd = new SecureRandom();
    private final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String DIGITS = "0123456789";
    private final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>?/~`|";

    public String generate(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("length must be >= 4");
        }

        String all = UPPER + LOWER + DIGITS + SPECIAL;
        List<Character> chars = new ArrayList<>();

        // Гарантируем наличие хотя бы по одному символу из каждой категории
        chars.add(UPPER.charAt(rnd.nextInt(UPPER.length())));
        chars.add(LOWER.charAt(rnd.nextInt(LOWER.length())));
        chars.add(DIGITS.charAt(rnd.nextInt(DIGITS.length())));
        chars.add(SPECIAL.charAt(rnd.nextInt(SPECIAL.length())));

        // Остальные символы выбираем из полного набора
        for (int i = 4; i < length; i++) {
            chars.add(all.charAt(rnd.nextInt(all.length())));
        }

        // Перемешиваем для случайного порядка
        Collections.shuffle(chars, rnd);

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
}
