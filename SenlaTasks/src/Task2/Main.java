package Task2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter();

        System.out.println("Курсы валют будут заданы в формате: 1 USD = X <валюта>");
        System.out.print("Использовать встроенные курсы по умолчанию? (y/n): ");
        String choice = in.nextLine().trim();

        if (choice.equalsIgnoreCase("n")) {
            System.out.println("Введите курсы для пяти валют (сколько единиц валюты = 1 USD).");
            for (int i = 0; i < converter.getCurrencyCount(); i++) {
                while (true) {
                    System.out.print("Курс для " + converter.getCurrencyName(i) + " (1 USD = ? " + converter.getCurrencyName(i) + "): ");
                    if (in.hasNextDouble()) {
                        double rate = in.nextDouble();
                        in.nextLine();
                        if (rate > 0.0) {
                            converter.setRatePerUsd(i, rate);
                            break;
                        } else {
                            System.out.println("Курс должен быть положительным числом.");
                        }
                    } else {
                        System.out.println("Ошибка ввода. Введите число с плавающей точкой.");
                        in.nextLine();
                    }
                }
            }
        } else {
            System.out.println("Использую встроенные курсы по умолчанию.");
        }

        System.out.println();
        System.out.println("Список валют:");
        for (int i = 0; i < converter.getCurrencyCount(); i++) {
            System.out.println(i + " - " + converter.getCurrencyName(i));
        }

        int srcIndex = -1;
        while (true) {
            System.out.print("Выберите номер валюты, из которой конвертировать (0-" + (converter.getCurrencyCount()-1) + "): ");
            if (in.hasNextInt()) {
                srcIndex = in.nextInt();
                in.nextLine();
                if (srcIndex >= 0 && srcIndex < converter.getCurrencyCount()) {
                    break;
                } else {
                    System.out.println("Номер вне диапазона.");
                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                in.nextLine();
            }
        }

        double amount = 0.0;
        while (true) {
            System.out.print("Введите сумму в " + converter.getCurrencyName(srcIndex) + ": ");
            if (in.hasNextDouble()) {
                amount = in.nextDouble();
                in.nextLine();
                if (amount >= 0.0) {
                    break;
                } else {
                    System.out.println("Сумма не может быть отрицательной.");
                }
            } else {
                System.out.println("Ошибка ввода. Введите число (может быть с плавающей точкой).");
                in.nextLine();
            }
        }

        System.out.println();
        System.out.println("Результаты конвертации для " + amount + " " + converter.getCurrencyName(srcIndex) + ":");
        for (int i = 0; i < converter.getCurrencyCount(); i++) {
            if (i == srcIndex) continue;
            double converted = converter.convert(amount, srcIndex, i);
            System.out.printf("%s -> %s: %.4f%n", converter.getCurrencyName(srcIndex), converter.getCurrencyName(i), converted);
        }

        in.close();
    }
}


class CurrencyConverter {
    private final String[] names = {"USD", "EUR", "GBP", "JPY", "RUB"};
    private final double[] unitsPerUsd;

    public CurrencyConverter() {
        unitsPerUsd = new double[names.length];
        unitsPerUsd[0] = 1.0;
        unitsPerUsd[1] = 0.92;
        unitsPerUsd[2] = 0.80;
        unitsPerUsd[3] = 148.0;
        unitsPerUsd[4] = 96.5;
    }

    public int getCurrencyCount() {
        return names.length;
    }

    public String getCurrencyName(int idx) {
        return names[idx];
    }

    public void setRatePerUsd(int idx, double unitsPerUsdValue) {
        unitsPerUsd[idx] = unitsPerUsdValue;
    }


    public double convert(double amount, int srcIndex, int tgtIndex) {
        double amountUsd = amount / unitsPerUsd[srcIndex];
        return amountUsd * unitsPerUsd[tgtIndex];
    }
}
