package me.spoony.autoggplus.utils;

public class ConvertDropdown {

    // Array of GG messages corresponding to dropdown indices
    private static final String[] GG_MESSAGES = {
            "gg",           // 0
            "GG",           // 1
            "gg wp",        // 2
            "GG WP",        // 3
            "gf",           // 4
            "gg gf",        // 5
            "Good Game",    // 6
            "Good Fight"    // 7
    };

    public static String getGGMessage(int index) {
        if (index >= 0 && index < GG_MESSAGES.length) {
            return GG_MESSAGES[index];
        } else {
            return GG_MESSAGES[0];
        }
    }
}