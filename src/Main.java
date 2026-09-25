import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author [your name here]
 *
 * This class holds the main() method for the sentiment analysis program.
 */

public class Main {

    public static void main(String[] args) {
        // implement this method in Step 4


        if(args.length == 0 || args[0] == null || args[0].isEmpty()){
            System.out.println("no input file");
            return;
        }

        Set<Sentence> sentences = Reader.readFile(args[0]);

        if(sentences == null) {
            System.out.println("bad input file");
            return;
        }

        Map<String, Double> scoreMap = Analyzer.calculateWordScores(sentences);
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("Enter a sentence: ");

            try{

                if (!in.hasNextLine()) {
                    break;
                }

                String input = in.nextLine(); // read the entire line that was entered

                if(input.equals("quit")){
                    break;
                }

                double result = Analyzer.calculateSentenceScore(scoreMap, input);
                System.out.println(result);
            }
            catch (Exception e) {
                // oops! something went wrong
                e.printStackTrace();
            }
        }

        in.close();
    }
}
