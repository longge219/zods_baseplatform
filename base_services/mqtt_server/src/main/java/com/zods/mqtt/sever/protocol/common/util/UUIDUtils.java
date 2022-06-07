package com.zods.mqtt.sever.protocol.common.util;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UUIDUtils {

    private static String[] randomValues = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "u",
            "t", "s", "o", "x", "v", "p", "q", "r", "w", "y", "z"};

    private static String[] numRandomValues = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String getUsername(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (randomValues.length - 1);
            str.append(randomValues[number.intValue()]);
        }

        return str.toString();
    }

    public static String getNumber(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (numRandomValues.length - 1);
            str.append(numRandomValues[number.intValue()]);
        }

        return str.toString();
    }

    public static String getUUIDChars(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (randomValues.length - 1);
            str.append(randomValues[number.intValue()]);
        }

        return str.toString();
    }

    public static String getUUIDNumbers(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (numRandomValues.length - 1);
            str.append(numRandomValues[number.intValue()]);
        }

        return str.toString();
    }

    public static String getRandomBasedUUID() {
        UUID uuid = Generators.randomBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

    public static String getTimeBasedUUID() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.getUsername(6));
    }
}
