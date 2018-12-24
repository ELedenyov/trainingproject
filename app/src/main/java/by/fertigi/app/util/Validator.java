package by.fertigi.app.util;

import java.util.regex.Pattern;

public class Validator {
    private static String REG_EX_MATCHES = "[\\u0020-\\u007e]+";
    private static String REG_EX_REPLACE = "[^\\u0020-\\u007e]";
    private static String STRING_EMPTY = "";

    /**
     * This method removes invalid characters in the string.
     * @param str - the line in which you want to delete characters
     * @return string without invalid characters
     */
    public static String replace(String str) {
        return str.replaceAll(REG_EX_REPLACE, STRING_EMPTY);
    }

    /**
     * This method consists in checking the string for invalid characters.
     * @param str - String to check
     * @return false - if there are characters other than range [U+0020 - U+007E] or true - if there are no characters except range [U+0020 - U+007E]
     */
    public static boolean contains(String str){
        return Pattern.matches(REG_EX_MATCHES, str);
    }
}
