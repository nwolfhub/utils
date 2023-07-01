package org.nwolfhub.utils;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

public class Utils {
    public static Random random = new Random();
    public static String[] characters;

    static {
        String charS = "abcdefghijklmnopqrstuvwxyz";
        charS+=charS.toUpperCase() + "01234567890";
        characters = charS.split("");
    }


    public static String generateString(int length) {
        StringBuilder result = new StringBuilder();
        for(int now = 0; now<length; now++) {
            result.append(characters[random.nextInt(characters.length)]);
        }
        return result.toString();
    }

    public static HashMap<String, String> parseValues(String[] text) {
        HashMap<String, String> map = new HashMap<>();
        for (String record:text) {
            if(!record.split("")[0].equals("#")) {
                if (record.contains("=")) {
                    map.put(record.split("=")[0], record.split("=")[1]);
                }
            }
        }
        return map;
    }
    public static HashMap<String, String> parseValues(String text, String separator) {
        return parseValues(text.split(separator));
    }

    public static String buildConnectionString(String address, Integer port, String database) {
        return "jdbc:postgresql://" + address + ":" + port + "/" + database;
    }
    public static String buildConnectionString(String driver, String address, Integer port, String database) {
        return "jdbc: " + driver + "://" + address + ":" + port + "/" + database;
    }
    public static String hashString(String text, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return DatatypeConverter.printBase64Binary(digest.digest(text.getBytes()));
    }

    public static String hashString(String text) throws NoSuchAlgorithmException {
        return hashString(text, "SHA-256");
    }

    public static void typeText(String prevText, boolean remove, String pretext, String text, int delayBetween, int removeDelay, int midDelay, TextAction action) {
        new Thread(() -> {
            if(remove) {
                StringBuilder actual = new StringBuilder(prevText);
                while (actual.length()>0) {
                    actual.setLength(actual.length() - 1);
                    action.applyText(actual.toString());
                    try {
                        Thread.sleep(removeDelay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            StringBuilder builder = new StringBuilder(pretext);
            try {
                Thread.sleep(midDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String[] textSplit = text.split("");
            for(int i = 0; i<text.length(); i++) {
                builder.append(textSplit[i]);
                try {
                    Thread.sleep(delayBetween);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                action.applyText(builder.toString());
            }
        }).start();
    }

    public static void typeText(String text, int delayBetween, TextAction action) {
        typeText("", false, "", text, delayBetween, 0, 0, action);
    }

}
