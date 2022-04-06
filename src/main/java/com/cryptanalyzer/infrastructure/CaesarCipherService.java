package com.cryptanalyzer.infrastructure;

public class CaesarCipherService {

    private static final String ALPHABET = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,\":-?! ";
    private static final int ALPHABET_LENGTH = ALPHABET.length();

    public String encrypt(String message, int offset) {
        var result = new StringBuilder();
        for (char character : message.toCharArray()) {
            int index = ALPHABET.indexOf(character);
            if (index == -1) {
                result.append(character);
                continue;
            }
            int newIndex = (index + offset) % ALPHABET_LENGTH;
            char newCharacter = ALPHABET.toCharArray()[newIndex];
            result.append(newCharacter);
        }

        return result.toString();
    }

    public String decipher(String message, int offset) {
        return encrypt(message, ALPHABET_LENGTH - (offset % ALPHABET_LENGTH));
    }

    public String bruteForceDecipher(String message) {
        for (var i = 0; i < ALPHABET_LENGTH; i++) {
            var result = encrypt(message, ALPHABET_LENGTH - (i % ALPHABET_LENGTH));

            if (checkDecryption(result)) {
                return result;
            }
        }

        return message;
    }

    private boolean checkDecryption(String message) {
        if (checkBorderPunctuationMark(message, '.'))
            return false;
        if (checkBorderPunctuationMark(message, '"'))
            return false;
        if (checkBorderPunctuationMark(message, '!'))
            return false;
        if (checkBorderPunctuationMark(message, '?'))
            return false;
        if (checkInsidePunctuationMark(message, ','))
            return false;
        if (checkInsidePunctuationMark(message, ':'))
            return false;
        if (checkInsidePunctuationMark(message, '-'))
            return false;
        if (checkSpaceMark(message))
            return false;

        return true;
    }

    private boolean checkBorderPunctuationMark(String message, char mark) {
        var index = message.indexOf(mark);

        if (index != -1) {
            if (index == message.length() - 1) {
                return false;
            } else {
                if (message.toCharArray()[index + 1] != ' ') {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private boolean checkInsidePunctuationMark(String message, char mark) {
        var index = message.indexOf(mark);

        if (index != -1) {
            if (index == message.length() - 1 || index == 0) {
                return true;
            } else {
                if (message.toCharArray()[index + 1] != ' ') {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private boolean checkSpaceMark(String message) {
        var mark = ' ';
        var index = message.indexOf(mark);

        if (index != -1) {
            if (index == message.length() - 1 || index == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
