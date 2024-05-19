package Project4;
import java.util.*;

public class BoggleCube {
	
    private char[] letters;	
	
	//creates a boggle cube with the given letters
	private BoggleCube(char[] letters) {
        this.letters = letters;
	}
	
	//returns a random letter from the boggle cube
	public char getRandomLetter() {
        Random rand = new Random();
        int randomindex = rand.nextInt(letters.length);
        return letters[randomindex];
	}
	
	//creates and returns a list of boggle cubes
	//each row in the parameter represents one cube
	//Precondition: the parameter has sixteen rows
	public static List<BoggleCube> makeCubes(char[][] allLetters){
	      List<BoggleCube> wordList = new ArrayList<>();
	        for (char[] allLetter : allLetters) {
	        	wordList.add(new BoggleCube(allLetter));
	        }
	        return wordList;
	}
}
