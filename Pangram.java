package Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Pangram {

	// pangram field which has all the words
	private static String pangram = new String();

	// this holds the letters not in pangram
	private static HashSet<Character> letterNotInPangram = new HashSet<>();

	// This field holds all the character in the pangram words
	private static HashSet<Character> letterInPangram = new HashSet<Character>();

	// after file read we store the words in wordList field
	private static List<String> WordDictionary = new ArrayList<String>();


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String filePath = "/Users/nirojghale/CUNY/Java/src/Project/words.txt"; // Replace with the actual file path

		// create dictionary of words
		WordDictionary = readTextFile(filePath);

		// create a copy as original to be used in the help function
		List<String> OriginalDictionary = new ArrayList<String>(WordDictionary);

		// By default fill all the letters and later on we keep removing from the list
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			letterNotInPangram.add(ch);
		}

		System.out.println("Welcome to Pangram Maker!");
		Scanner input = new Scanner(System.in);
		while (letterInPangram.size() <= 26){
	
			if (letterInPangram.size() == 26) {
				break;
			}
			
			System.out.println("\nYour pangram so far is: "+pangram);
			//System.out.println("In Pangram: "+letterInPangram);
			//System.out.println("Not In Pangram: "+letterNotInPangram);
			
			System.out.print("Enter the next word (in all uppercase) or enter help for suggestions:");
			String userInput = input.next();

			//if user type help
			if (userInput.equals("help")) {
				//System.out.println("Help is required.");
				displayHelp(OriginalDictionary);
			} else {
				// check if the user
				if (isWordExistInPangram(userInput,OriginalDictionary) ) {
					// add word to the Pangram
					
					pangram += userInput+" ";
					
					

					// Remove words from dictinary so that we can find remaining words
					WordDictionary.remove(userInput);

					// add characters of pangram in the alphabet list and remove from not in pangramlist
					addLetterToList(userInput);

					// add to pangram
				} else {
					// display word not found error
					System.out.println("That's not a valid word!");
				}					
			}
		}
		input.close();
		System.out.println("\nYour pangram is complete!");
		System.out.println("Total Words: "+pangram.split("\\s+").length);
		int letterCount = countLettersInSentence(pangram);
		System.out.println("Total Letters:"+letterCount);
	}

	private static int countLettersInSentence(String sentence) {
        // Initialize counter for letters
        int letterCount = 0;
  
        // Iterate through each character of the sentence
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);

            // Check if the character is a letter (excluding space)
            if (Character.isLetter(c)) {
                letterCount++;
            }
        }
        return letterCount;
    }
	
	/**
	 * Read content from file
	 * @param filePath
	 * @return List<String>
	 */
	private static List<String> readTextFile(String filePath){
		List<String> wordsFromFile = new ArrayList<String>();

		File file = new File(filePath);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				wordsFromFile.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred: "+e.getMessage());
		}
		// ad extra two single letter words
		wordsFromFile.add("A");
		wordsFromFile.add("I");
		return wordsFromFile;
	}

	/**
	 * Display Help
	 * @param originalDict List<String> 
	 */
	private static void displayHelp(List<String> originalDict) {
		
		// Create list of words which is not been used in the pangram
		List<String> helpWords = new ArrayList<String>();
		
		// Try to find max word length and the word
		int currentMaxLength = 0;
		
		// loop through the words
		for(String word: WordDictionary) {
			
			//check if the letter not in the pangram
			for (char includeChar : letterNotInPangram) {
				if(word.contains(Character.toString(includeChar))) {

					// find max length of the words
					int maxDistinctWordLength = getDistinctWordLength(word);
					if (maxDistinctWordLength > currentMaxLength) {
						currentMaxLength = maxDistinctWordLength;
					}
					// if found stop searching further
					break;
				}
			}  
		}

		// loop through the word and capture all the words that has max length letters
		for(String s: WordDictionary) {
			// if word length is 15 then add those words in the final list
			if(getDistinctWordLength(s) == currentMaxLength ) {
//				System.out.println(s+" has length "+getDistinctWordLength(s));
				helpWords.add(s);
			}
		}
		// display random words top help words
		displayRandomWords(helpWords, originalDict);
	}
	
	/**
	 * Get the max length of distinct letters
	 * @param String distinctWord
	 * @return Integer
	 */
	private static int getDistinctWordLength(String distinctWord) {
		Set<Character> distinctLetters = new HashSet<>();
		for (char letter : distinctWord.toCharArray()) {
			distinctLetters.add(letter);
		}
		return distinctLetters.size();
	}

	/**
	 * Display random words
	 * @param helpWordsArgs List<String>
	 * @param originalDict List<String>
	 */
	private static void displayRandomWords(List<String> helpWordsArgs, List<String> originalDict) {

		// Display 5 words in the help
		int helpWordNumber = 5;
		Random random = new Random();

		// if the final help list has less than 5 words
		if (helpWordsArgs.size() < helpWordNumber) {

			// find how many we need to add to make it 5
			int remainingWords = helpWordNumber - helpWordsArgs.size();

			//System.out.println("Total: "+helpWordNumber+" Minus - " + helpWordsArgs.size()+" Remaining = "+remainingWords + "=="); 
			//System.out.println("Getting list of Original:"+originalDict.size());

			// Add remaining number of words from the Original list
			for (int i = 0; i < remainingWords; i++) {
				// Randomize the index
				int randomIndex = random.nextInt(originalDict.size());
				// Get the random words using random index
				String randomWord = originalDict.get(randomIndex);
				
				// Add the word in the top 5 list
				helpWordsArgs.add(randomWord); 
			}
		}

		// Finally print the list of words
		for (int i = 0; i < helpWordNumber; i++) {
			int randomNumber = random.nextInt(helpWordsArgs.size());
			System.out.println(helpWordsArgs.get(randomNumber));
		}
	}

	/**
	 * Check the word if it exist in Pangram
	 * @param userInputWord String
	 * @return Boolean
	 */
	private static boolean isWordExistInPangram(String userInputWord, List<String> originalDict) {
		boolean wordFound = false;
		for(String wr: originalDict) {
			if (wr.equals(userInputWord)) {
				wordFound = true;
				break;
			} 
		}
		return wordFound;
	}
	
	/**
	 * Add letter in Pangram and remove from Not in Pangram
	 * @param userInputWord
	 */
	private static void addLetterToList(String userInputWord) {
		// convert the string to character array and loop through each letter
		for (char ch : userInputWord.toCharArray()) {
			// check if each character is a valid letter
			if (Character.isLetter(ch)) {
				// add letter in the Pangram
				letterInPangram.add(ch);
				// Remove letter from Not in Pangram
				letterNotInPangram.remove(ch);
			}
		}
	}
}
