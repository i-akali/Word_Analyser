/**
 * @author [YOUR NAME HERE!]
 *
 * This class contains a method for reading from a file and creating Sentence objects
 * for a sentiment analysis program.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Reader {
    /**
     * This method reads sentences from the input file, creates a Sentence object
     * for each, and returns a Set of the Sentences.
     *
     * @param filename Name of the input file to be read
     * @return Set containing one Sentence object per sentence in the input file; null if filename is null
     */
    public static Set<Sentence> readFile(String filename) {
        /*
         * Implement this method in Step 1
         */

        BufferedReader reader;
        Set<Sentence> reviews = new HashSet<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                try {
                    int num = Integer.parseInt(line.split("\\s+")[0]);
                    if (Math.abs(num) > 2) {
                        throw new IllegalArgumentException();
                    }
                    String review = line.split(num + "\\s+")[1];
                    reviews.add(new Sentence(num, review));
                } catch (Exception e) {
                    // Silently ignore malformed lines

                }

                // Read the next line outside the inner try-catch so it always executes
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Handle file reading exceptions
            return null;
        }

        return reviews;
    }

    /**
     * Use this main() method for testing your Reader.readFile method with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {
        Set<Sentence> words = Reader.readFile("reviews.txt");

        if(words == null) {
            return;
        }
        for(Sentence sentence: words)
        {
            System.out.println("score: "+ sentence.getScore() + " review: " + sentence.getText());
        }

    }
}
