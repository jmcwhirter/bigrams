import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    public void testReadFile() {
        Main program = new Main();
        String fileContent = program.readFile("filename.txt");

        assertEquals("The quick brown fox and the quick blue hare.", fileContent);
    }

    @Test
    public void testGatherBigram() {
        Main program = new Main();
        Map<String, Integer> actualBigrams = program.gatherBigrams("The quick brown fox and the quick blue hare.");
        Map<String, Integer> expectedBigrams;
        expectedBigrams = new HashMap<>();
        expectedBigrams.put("the quick", 2);
        expectedBigrams.put("quick brown", 1);
        expectedBigrams.put("brown fox", 1);
        expectedBigrams.put("fox and", 1);
        expectedBigrams.put("and the", 1);
        expectedBigrams.put("quick blue", 1);
        expectedBigrams.put("blue hare", 1);

        assertEquals(expectedBigrams.size(), actualBigrams.size(), "Sizes are not equal.");
        assertTrue(actualBigrams.equals(expectedBigrams), "Maps are not equal.");
    }

    @Test
    public void testSanitize() {
        Main program = new Main();
        String sanitizedContent = program.sanitize("The quick brown fox; and the quick, blue, hare!");

        assertEquals("the quick brown fox and the quick blue hare", sanitizedContent, "Input not sanitized properly.");
    }
}