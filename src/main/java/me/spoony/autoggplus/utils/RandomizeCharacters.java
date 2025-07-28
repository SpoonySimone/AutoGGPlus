package me.spoony.autoggplus.utils;

import java.util.Random;

public class RandomizeCharacters {
    private static String lastResult;
    private static final Random random = new Random();

    public static String randomize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result;

        // keep trying until result is different from last one
        do {
            result = randomizeCapitalization(input);
        } while (result.equals(lastResult));

        lastResult = result;
        return result;
    }

    private static String randomizeCapitalization(String input) {
        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                if (random.nextBoolean()) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}