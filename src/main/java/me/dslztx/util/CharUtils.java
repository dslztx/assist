package me.dslztx.util;

/**
 * Char Utility
 * 
 * @author dslztx
 */
public class CharUtils {

    public static boolean isHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static boolean isOctChar(char c) {
        return (c >= '0' && c <= '7');
    }

    public static boolean isDecimalChar(char c) {
        return isDigitChar(c);
    }

    public static boolean isDigitChar(char c) {
        return (c >= '0' && c <= '9');
    }

}
