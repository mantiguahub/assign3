//Name:Michael Antigua
//Cmsc 203


import java.util.;

public class CryptoManager {

    public static final char LOWER_RANGE = ' '; 
    public static final char UPPER_RANGE = '_';
    public static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;

    public static boolean isStringInBounds(String plainText) {
        for (char c : plainText.toCharArray()) {
            if (c < LOWER_RANGE || c > UPPER_RANGE) {
                return false;
            }
        }
        return true;
    }

    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText))
            return "The selected string is not in bounds, Try again.";

        StringBuilder result = new StringBuilder();
        String fullKey = repeatKeyToLength(key, plainText.length());

        for (int i = 0; i < plainText.length(); i++) {
            char p = plainText.charAt(i);
            char k = fullKey.charAt(i);
            int shifted = ((p - LOWER_RANGE) + (k - LOWER_RANGE)) % RANGE + LOWER_RANGE;
            result.append((char) shifted);
        }

        return result.toString();
    }

    public static String vigenereDecryption(String encryptedText, String key) {
        StringBuilder result = new StringBuilder();
        String fullKey = repeatKeyToLength(key, encryptedText.length());

        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            char k = fullKey.charAt(i);
            int shifted = ((c - k + RANGE) % RANGE) + LOWER_RANGE;
            result.append((char) shifted);
        }

        return result.toString();
    }

    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText))
            return "The selected string is not in bounds, Try again.";

        char[][] matrix = buildPlayfairMatrix(key);
        String preparedText = preparePlayfairText(plainText);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < preparedText.length(); i += 2) {
            char a = preparedText.charAt(i);
            char b = preparedText.charAt(i + 1);
            result.append(encryptPlayfairPair(a, b, matrix, true));
        }

        return result.toString();
    }

    public static String playfairDecryption(String encryptedText, String key) {
        char[][] matrix = buildPlayfairMatrix(key);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i += 2) {
            char a = encryptedText.charAt(i);
            char b = encryptedText.charAt(i + 1);
            result.append(encryptPlayfairPair(a, b, matrix, false));
        }

        return result.toString();
    }

    private static String repeatKeyToLength(String key, int length) {
        StringBuilder fullKey = new StringBuilder();
        while (fullKey.length() < length) {
            fullKey.append(key);
        }
        return fullKey.substring(0, length);
    }

    private static char[][] buildPlayfairMatrix(String key) {
        Set<Character> seen = new LinkedHashSet<>();
        for (char c : key.toCharArray()) {
            if (c >= LOWER_RANGE && c <= UPPER_RANGE) {
                seen.add(c);
            }
        }

        for (char c = LOWER_RANGE; c <= UPPER_RANGE; c++) {
            seen.add(c);
        }

        char[][] matrix = new char[8][8];
        Iterator<Character> it = seen.iterator();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (it.hasNext()) {
                    matrix[row][col] = it.next();
                }
            }
        }

        return matrix;
    }

    private static String preparePlayfairText(String text) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char a = text.charAt(i++);
            char b = (i < text.length()) ? text.charAt(i++) : 'X';
            if (a == b) { // repeat case
                sb.append(a).append('X');
                i--; 
            } else {
                sb.append(a).append(b);
            }
        }
        if (sb.length() % 2 != 0) {
            sb.append('X'); 
        }
        return sb.toString();
    }

    private static String encryptPlayfairPair(char a, char b, char[][] matrix, boolean encrypt) {
        int[] posA = findPosition(matrix, a);
        int[] posB = findPosition(matrix, b);

        if (posA[0] == posB[0]) { 
            int colA = (encrypt) ? (posA[1] + 1) % 8 : (posA[1] + 7) % 8;
            int colB = (encrypt) ? (posB[1] + 1) % 8 : (posB[1] + 7) % 8;
            return "" + matrix[posA[0]][colA] + matrix[posB[0]][colB];
        } else if (posA[1] == posB[1]) { 
            int rowA = (encrypt) ? (posA[0] + 1) % 8 : (posA[0] + 7) % 8;
            int rowB = (encrypt) ? (posB[0] + 1) % 8 : (posB[0] + 7) % 8;
            return "" + matrix[rowA][posA[1]] + matrix[rowB][posB[1]];
        } else { 
            return "" + matrix[posA[0]][posB[1]] + matrix[posB[0]][posA[1]];
        }
    }

    private static int[] findPosition(char[][] matrix, char c) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (matrix[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null; 
    }
}
