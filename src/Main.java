import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String SPLIT_CHAR = " ";

    public static void main(String[] args) {
        Main program = new Main();
        try {
            String input = program.readFile(args[0]);
            Map<String, Integer> bigrams = program.gatherBigrams(input);
            program.sortAndPrint(bigrams);
        } catch (ArrayIndexOutOfBoundsException oobe) {
            System.out.println("No args given.");
            System.exit(1);
        }
    }

    public String readFile(String filename) {
        String fileContents = "";
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException ioe) {
            System.out.println("Problem reading file.");
            ioe.printStackTrace();
            System.exit(1);
        }
        return fileContents;
    }

    public Map gatherBigrams (String text) {
        Map<String, Integer> bigrams = new LinkedHashMap<>();
        String word1, word2;

        text = sanitize(text);
        String[] words = text.split(SPLIT_CHAR);
        for (int i = 1; i < words.length; i++) {
            word1 = words[i - 1];
            word2 = words[i];

            String bigram = word1.concat(" ").concat(word2);

            if (!bigrams.containsKey(bigram)) {
                bigrams.put(bigram, 1);
            } else {
                bigrams.put(bigram, bigrams.get(bigram) + 1);
            }
        }

        return bigrams;
    }

    public String sanitize(String text) {
        return text.toLowerCase().trim().replaceAll("[^A-Za-z0-9']", " ").replaceAll(" +", " ");
    }

    public void sortAndPrint(Map<String, Integer> bigrams) {
        Map<String, Integer> sortedMap = bigrams.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            System.out.println("\"" + entry.getKey() + "\" " + entry.getValue());
        }
    }
}
