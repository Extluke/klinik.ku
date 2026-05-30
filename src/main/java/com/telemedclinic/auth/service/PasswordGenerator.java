package com.telemedclinic.auth.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final int DEFAULT_LENGTH = 12;
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private PasswordGenerator() {
    }

    public static String generate() {
        List<Character> passwordCharacters = new ArrayList<>();

        passwordCharacters.add(randomCharacterFrom(UPPERCASE));
        passwordCharacters.add(randomCharacterFrom(LOWERCASE));
        passwordCharacters.add(randomCharacterFrom(DIGITS));
        passwordCharacters.add(randomCharacterFrom(SYMBOLS));

        while (passwordCharacters.size() < DEFAULT_LENGTH) {
            passwordCharacters.add(randomCharacterFrom(ALL_CHARACTERS));
        }

        Collections.shuffle(passwordCharacters, SECURE_RANDOM);

        StringBuilder password = new StringBuilder(DEFAULT_LENGTH);
        for (Character character : passwordCharacters) {
            password.append(character);
        }

        return password.toString();
    }

    private static char randomCharacterFrom(String characters) {
        return characters.charAt(SECURE_RANDOM.nextInt(characters.length()));
    }
}
