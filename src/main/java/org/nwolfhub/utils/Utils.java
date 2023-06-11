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

}
