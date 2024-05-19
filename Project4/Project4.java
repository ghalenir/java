package Project4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
public class Project4 {

	private Set<String> wordsDictionary;
	public Project4() {
		wordsDictionary = new HashSet<>();
	}

	/**
	 * Load words from dictionary to the memory
	 */
	private void loadWordsInDictionary() {
		String filePath = "/Users/nghale/CS/JavaLab/Lab2/src/Project4/words11.txt"; // Path to your text file
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				wordsDictionary.add(line.trim());
			}

		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}

	/**
	 * Check if the word exists in dictionary
	 * @param word
	 * @return
	 */
	boolean isWordExist(String word) {
		return wordsDictionary.contains(word.toUpperCase());
	}


	void printLetters(List<BoggleCube> boogleCubeList) {
		char[][] grid = new char[4][4];
		int c = 0;
		int r = 0;
		for (BoggleCube row : boogleCubeList) {
			char rand = row.getRandomLetter();
			grid[r][c] = rand;
			
			// reset row
			if (c == 3) {
				c = 0;
			} else {
				c++;
			}

			// reset column
			if (c % 4 == 0) {
				r++;
			}
		}

		// print character in 4X4 format
		for (char[] row : grid) {
			for (char c1 : row) {
				System.out.print(c1 + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Custom recursive fibonacci
	 * @param n
	 * @return
	 */
	public int fibonacciSequencePoints(int n) {
		if (n <= 4) {
			return 1;
		}
		return fibonacciSequencePoints(n - 1) + fibonacciSequencePoints(n - 2);
	}

	/**
	 * Calcualte points based on fibonacci
	 * @param word
	 * @return
	 */
	int getCalculatePoints(String word) {
		return fibonacciSequencePoints(word.length());
	}

	// start main method
	public static void main(String[] args) {

		int maxPoints = 100; //Max points
		ArrayList<BogglePlayer> bogglePlayers = new ArrayList<BogglePlayer>();
	    List<String> eachRoundWordList = new ArrayList<String>(); //Word list for each round

		Project4 p4 = new Project4();
		// load dictionary to the memory
		p4.loadWordsInDictionary();
		
		// define char array of 16
		int displayBoardSize = 16;
		char[][] charArray = new char[displayBoardSize][];
		
		Scanner playerScanner = new Scanner(System.in);
		System.out.println("Enter the cube faces as sixteen lines below:");
		for(int i=0; i<displayBoardSize; i++) {
			charArray[i] = playerScanner.next().toCharArray();
		}
		
		List<BoggleCube> cubesList = BoggleCube.makeCubes(charArray);

		System.out.println("How many players?");
		int playerNumber = playerScanner.nextInt(); // may need to validate if its integer input

		// Create and store multiple BogglePlayer Object
		for (int i = 0; i < playerNumber; i++) {
			System.out.print("Enter player " + (i + 1) + "'s name (no spaces):");
			String name = playerScanner.next();
			bogglePlayers.add(new BogglePlayer(name));
		}
		// print letter
		p4.printLetters(cubesList);

		int playerIndex = 0;
		boolean gameOver = false;
		// loop until game is over based on the player score
		while (!gameOver) {
			System.out.println("Enter " + bogglePlayers.get(playerIndex).getName() + "'s words ("+bogglePlayers.get(playerIndex).getScore()+" points), then enter done.");

			// loop until the user type the word done.
			boolean isDone = false;
			while (!isDone) {
				String playerIputWord = playerScanner.next();
				if (playerIputWord.equalsIgnoreCase("done")) {
					// if the user has typed the word done, go to next player and exit the player loop
					playerIndex++;
					break;
				}

				// check if this word exist in the dictionary
				// if it contains at least 3 word
				// calculate the point and store the score
				// check if the word already used by you or any other player for this round
				if (playerIputWord.length() < 3) {
					System.out.println("Words must contain at least three letters!");
				} else if (!p4.isWordExist(playerIputWord)) {
					System.out.println("Not a word in the dictionary!");
				} else if(eachRoundWordList.contains(playerIputWord)) {
					System.out.println("You or someone already used that word this round!");
				}
				else {
					// check if the word exist and
					// add words and points
						
						// Add the word to the current word list for this round.
						eachRoundWordList.add(playerIputWord);
						
						//add the word to the individual player's word list
						bogglePlayers.get(playerIndex).addWord(playerIputWord);
						
						//add points for each individual player
						bogglePlayers.get(playerIndex).addPoints(p4.getCalculatePoints(playerIputWord));
						
						// check if the current user has reached to target score after adding points 
						if (bogglePlayers.get(playerIndex).getScore() >= maxPoints) {
							
							//exit the outer loop
							gameOver = true;
							break;
						}
				}
			}
			
			// check the player index and reset to 0 if it has reached to max
			if (playerIndex >= playerNumber) {
				playerIndex = 0;
				
				//clear individual player words
				bogglePlayers.get(playerIndex).clearWords();
				
				// check this round word list
				eachRoundWordList.clear();
			}
			
			// If max point is reached display message and end the game
			if (bogglePlayers.get(playerIndex).getScore() >= maxPoints) {
				System.out.println(bogglePlayers.get(playerIndex).getName() + " has woon the game.");
				gameOver = true;
				break;
			}
		}
		// close scanner
		playerScanner.close();
		
		// print all player's name and score
		for (int i = 0; i < playerNumber; i++) {
			String playerName = bogglePlayers.get(i).getName();
			int playerScore = bogglePlayers.get(i).getScore();
			System.out.println(playerName+": "+playerScore);
		}
		
		// Print all letter in random order
		p4.printLetters(cubesList);

	}
}