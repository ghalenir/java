package Project4;

import java.util.*;

public class BogglePlayer {
	

	private int score;
	private String name;
	private List<String> wordList;

	//creates a boggle player with a given name
	public BogglePlayer(String name) {
		this.name = name;
		this.score = 0;
		wordList = new ArrayList<String>();
	}
	//returns the name of this boggle player
	public String getName() {
		return this.name;
	}
	
	//adds the given number of points to this player's score
	//player scores begin at zero
	public void addPoints(int points) {
		this.score += points;
	}
	//returns this player's score
	public int getScore() {
		return this.score;
	}
	//adds a word to this player's list of words
	public void addWord(String word) {
		wordList.add(word);
	}
	
	//returns this player's list of words
	public List<String> getWords(){
		return wordList;
	}
	//removes all words from this player's list of words
	public void clearWords() {
		wordList.clear();
	}
}
