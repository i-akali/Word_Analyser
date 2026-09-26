/**
 * @author [YOUR NAME HERE!]
 *
 * This class contains the methods used for conducting a simple sentiment analysis.
 */

import java.util.*;

public class Analyzer {

	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 * 
	 * @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average; null if input is null
	 */
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Step 2
		 */

        if (sentences == null) {
            return null;
        }

        Map<String, Double> wordTotalScores = new HashMap<>();
        Map<String, Integer> wordCounts = new HashMap<>();

        for (Sentence sentence : sentences) {
            // Skip null or invalid sentences
            if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty() || Math.abs(sentence.getScore()) > 2) {
                continue;
            }

            String text = sentence.getText().toLowerCase();
            StringTokenizer tokenizer = new StringTokenizer(text);

            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken();

                // Filter out words that don't start with a letter
                if (Character.isLetter(word.charAt(0))) {
                    wordTotalScores.put(word, wordTotalScores.getOrDefault(word, 0.0) + sentence.getScore());
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Calculate final averages: total score / total appearances
        Map<String, Double> result = new HashMap<>();
        for (String word : wordTotalScores.keySet()) {
            double averageScore = wordTotalScores.get(word) / wordCounts.get(word);
            result.put(word, averageScore);
        }

        return result;
	}
	
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 * 
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return Weighted average scores of all words in input sentence; null if either input is null
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Step 3
		 */
        if(wordScores == null || sentence == null || sentence.isEmpty() || wordScores.isEmpty()) {
            return 0;
        }

        double score = 0;
        int count = 0;

        sentence = sentence.toLowerCase();
        StringTokenizer tokens = new StringTokenizer(sentence);
        while (tokens.hasMoreTokens()) {
            String word = tokens.nextToken();
            if(!Character.isLetter(word.charAt(0))) {continue;}
            score += wordScores.getOrDefault(word, 0.0);
            count++;
        }
        return count == 0 ? 0 : score / count;
	}

    /**
     * Use this main() method for testing your calculateWordScores and
     * calculateSentenceScore methods with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {

        Set<Sentence> sentences = new HashSet<>();
        sentences.add(new Sentence(1, "I like dogs dogs"));
        Map<String, Double> scores = calculateWordScores(sentences);
        if (scores.get("dogs") != 1) {
            System.out.println("wrong score for dogs!");
            System.out.println(scores);
        }



    }

}
