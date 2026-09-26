import java.io.*;
import java.util.*;

/*
 * Implements a text search engine for a collection of documents in the same directory.
 */

public class WordSearch {
	
	public static Map<String, Set<String>> buildMap(String dirName) {
		File dir = new File(dirName);	// create a File object for this directory
		
		// make sure it exists and is actually a directory
		if (dir.exists() == false || dir.isDirectory() == false) {
            // this tells the caller "you gave me bad input"
			throw new IllegalArgumentException(dirName + " does not exist or is not a directory");
		}
		
		File[] files = dir.listFiles();		// get the Files in the specified directory
		
		// Implement the rest of this method starting from here!

        HashMap<String, Set<String>> database = new HashMap<>();

        if (files == null) {
            return database;
        }

        for (File file : files) {
            // Skip subdirectories if any exist
            if (file.isDirectory()) {
                continue;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String word = scanner.next().toLowerCase();

                    // Ensure word entry exists in map and add file name
                    database.computeIfAbsent(word, k -> new HashSet<>()).add(file.getName());
                }
            } catch (FileNotFoundException e) {
                // Handle or skip unreadable files
            }
        }


		// this is for debugging, just to make sure it's reading the right files
        for (File file : files) {
            System.out.println(file.getName());
        }
		
		return database; // change this as necessary
		
	}
	
	public static List<String> search(String[] terms, Map<String, Set<String>> map) {
		// Implement the Raking system!

        List<String> result = new ArrayList<>();

        if (terms == null || terms.length == 0 || map == null || map.isEmpty()) {
            return result;
        }

        //Keep track of file appearances.
        HashMap<String, Integer> count = new HashMap<>();

        for(String word: terms) {
            word = word.toLowerCase();
            if(map.get(word) != null) {
                List<String> temp = new ArrayList<>(map.get(word));
                for(String element: temp) {
                    if(!count.containsKey(element)){
                        count.put(element, 1);
                    }else{
                        int num = count.get(element);
                        count.remove(element);
                        count.put(element, num+1);
                    }
                }
            }
        }

        int matches = terms.length;
            for(int i = matches; i > 0 ; i--) {
                List<String> done = new ArrayList<>();
                for(String key: count.keySet()) {
                    int num = count.get(key);
                    if(num == i) {done.add(key);}
                }
                Collections.sort(done); // Sort lexicographically
                result.addAll(done);
            }

		return result; // change this as necessary
	}
	
	public static void main(String[] args) {
		Map<String, Set<String>> map = buildMap(args[0]);
		System.out.println(map); 					// for debugging purposes
		
		System.out.print("Enter a term to search for: ");
		
		try (Scanner in = new Scanner(System.in)) { // create a Scanner to read from stdin
			String input = in.nextLine();			// read the entire line that was entered
			String[] terms = input.split(" ");		// separate tokens based on a single whitespace
			List<String> list = search(terms, map);	// search for the tokens in the Map
			for (String file : list) {				// print the results
				System.out.println(file);
			}
		}
		catch (Exception e) {
			// oops! something went wrong
			e.printStackTrace();
		}
	}

}
