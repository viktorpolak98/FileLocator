package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameGenerator {

    private static final Random random = new Random();

    private static final List<String> words = new ArrayList<>();

    static {
        try (InputStream is = new FileInputStream("utilFiles\\words.txt")) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String word;
                while ((word = reader.readLine()) != null) {
                    words.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateName() {
        int nameLength = random.nextInt(6) + 2;

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < nameLength; j++) {
            int index = random.nextInt(words.size());
            sb.append(words.get(index));
            if (j < nameLength - 1) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
}