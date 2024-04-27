package Project;

import java.util.Scanner;

public class Tetris {

	public static void main(String[] args) {

		// define endGane flag to false
		boolean endGame = false;

		// instantiate and Scanner object
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the number of rows:");
		int rows = input.nextInt();

		System.out.print("Enter the number of columns:");
		int cols = input.nextInt();

		System.out.print("Enter the number of types:");
		int tileNumber = input.nextInt();

		char[][] charArray = new char[rows][cols];

		// Loop through the Game until the endGame flag is true
		while (!endGame) {

			char localTileType = getTileType(tileNumber);
			System.out.println("Next Tile: " + localTileType);
			displayGrid(charArray, rows, cols);

			// get column index based on the user input
			int columnIndex = getColumnIndex(charArray, rows, cols, input);

			// if column index is negative
			if (columnIndex < 0) {
				endGame = true;
				System.out.println("GAME OVER");
				break;
			}

			// set array values
			boolean matchTileStatus = setTileToGrid(charArray, rows, cols, columnIndex, localTileType);
			if (matchTileStatus) {
				displayGrid(charArray, rows, cols);
				System.out.println("You made a set! Enter any word to continue.");
				input.next();
				// set replaceStars flag to TRUE and this will replace matching column character
				// with star
				matchTile(charArray, true);
			}
		}
		input.close();

	}

	/**
	 * Returns tile type
	 * 
	 * @return char
	 */
	private static char getTileType(int tileNumber) {
		
		// define dynamic tile char array
		char[] tileTypes = new char[tileNumber];	
		for (int i = 0; i < tileNumber; i++) {
			tileTypes[i] = (char) (65 + i);
		}

		int min = 1, max = tileNumber;
		int range = max - min + 1;
		// generate random number between
		int rand = (int) (Math.random() * range) + min;

		// return the tileType of the random index
		return tileTypes[rand - 1];
	}

	/**
	 * 
	 * @param input Scanner object
	 * @return column index
	 */
	private static int getColumnIndex(char[][] charArray, int rows, int cols, Scanner input) {
		System.out.println("Enter the column:" + cols);
		int columnNumber = input.nextInt();

		// re-define the index
		// column 1 means index 0
		boolean hasSpot = false;
		int columnIndex = (columnNumber < 0) ? 0 : columnNumber - 1;
		// check if the value in this column row and column
		// loop through the rows and check their empty values

		for (int currentRow = rows - 1; currentRow >= 0; currentRow--) {
			if (charArray[currentRow][columnIndex] == '\0') {
				hasSpot = true;
				break;
			}
		}

		if (!hasSpot) {
			return -1;
		}

		return columnIndex;

	}

	/**
	 * 
	 * @param replaceStars - a flag either to replace Stars with blank or replace
	 *                     matching tiles with Stars
	 * @return boolean - returns true if matching tile replaced with stars
	 */
	private static boolean matchTile(char[][] charArray, boolean replaceStars) {
		boolean matchTile = false;
		// Horizontal check - check by Row
		for (int i = 0; i < charArray.length; i++) {
			for (int j = 0; j < charArray[i].length - 2; j++) {

				// Don't replace stars - fill matching tile with stars
				if (!replaceStars) {
					if ((charArray[i][j] != '\0' && charArray[i][j + 1] != '\0' && charArray[i][j + 2] != '\0')) {
						// match vertical character
						if ((charArray[i][j] == charArray[i][j + 1]) && (charArray[i][j + 1] == charArray[i][j + 2])) {
							// Debug to check array index
							// System.out.println("Horizontal
							// match:["+i+"]["+j+"]:["+i+"]["+j+1+"]:["+i+"]["+j+2+"]==>"+(char)charArray[i][j]+
							// ":"+(char)charArray[i][j+1]+":"+(char)charArray[i][j+2]);
							charArray[i][j] = '*';
							charArray[i][j + 1] = '*';
							charArray[i][j + 2] = '*';
							matchTile = true;
						}
					}
				}

				// Replace Stars - After matching tile is found we will make those stars empty
				if (replaceStars) {
					if ((charArray[i][j] == '*' && charArray[i][j + 1] == '*' && charArray[i][j + 2] == '*')) {
						// match vertical character
						// Debug check Stars index
						// System.out.println("Replacing Horizontal
						// match:["+i+"]["+j+"]:["+i+"]["+j+1+"]:["+i+"]["+j+2+"]==>"+(char)charArray[i][j]+
						// ":"+(char)charArray[i][j+1]+":"+(char)charArray[i][j+2]);
						// Replace the array index with blank
						charArray[i][j] = '\0';
						charArray[i][j + 1] = '\0';
						charArray[i][j + 2] = '\0';

						// change the index of an array
						// Move the entire row down by 1 step
						for (int x = charArray.length - 1; x > 0; x--) {
							for (int y = charArray[x].length - 1; y >= 0; y--) {
								// debug and check their index
								// System.out.println(" Slide Down [" + x + "][" + y +"] = [" + (x-1) + "][" + y
								// + "]");
								charArray[x][y] = charArray[x - 1][y];
							}
						}
						matchTile = true;
					}
				}

			}
		}

		// Vertical check - check by Column
		for (int column = 0; column < charArray[0].length; column++) {
			for (int row = 0; row < charArray.length; row++) {
				if (row < charArray.length - 2) {
					// Don't replace stars - fill matching tile with stars
					if (!replaceStars) {
						if ((charArray[row][column] != '\0' && charArray[row + 1][column] != '\0'
								&& charArray[row + 2][column] != '\0')) {
							if ((charArray[row][column] == charArray[row + 1][column])
									&& (charArray[row + 1][column] == charArray[row + 2][column])) {
								// Debug the index
								// System.out.println("vertical
								// Match:["+row+"]["+column+"]:["+row+1+"]["+column+"]:["+row+2+"]["+column+"]===>"+(char)charArray[row][column]+
								// ":"+(char)charArray[row+1][column]+":"+(char)charArray[row+2][column]);
								// now replace their index with Star
								charArray[row][column] = '*';
								charArray[row + 1][column] = '*';
								charArray[row + 2][column] = '*';
								matchTile = true;
							}
						}
					}
					// Replace Stars - After matching tile is found we will make those stars empty
					if (replaceStars) {
						if ((charArray[row][column] == '*' && charArray[row + 1][column] == '*'
								&& charArray[row + 2][column] == '*')) {
							// Debug the index
							// System.out.println("Replacing Vertical
							// Match:["+row+"]["+column+"]:["+row+1+"]["+column+"]:["+row+2+"]["+column+"]===>"+(char)charArray[row][column]+
							// ":"+(char)charArray[row+1][column]+":"+(char)charArray[row+2][column]);
							charArray[row][column] = '\0';
							charArray[row + 1][column] = '\0';
							charArray[row + 2][column] = '\0';
							matchTile = true;
						}
					}
				}
			}
		}
		return matchTile;
	}

	/**
	 * This method will fill the specified tile eg 'A' to specified column index.
	 * 
	 * @param currentColumnIndex - target column
	 * @param tileType           - eg 'A'
	 * @return
	 */
	private static boolean setTileToGrid(char[][] charArray, int rows, int cols, int currentColumnIndex,
			char tileType) {
		// start from the bottom and go up
		// first returns 5-1 = index 4
		// next should return 4-1 = index 3
		// next should return 3-1 index 2
		// next should return 2-1 index 1
		// next should return 1-0 index 0

		// check if this column has already filled up or not
		// find if the row already filled up or not

		int nextEmptyRowIndex = rows - 1;
		for (int currentRow = rows - 1; currentRow >= 0; currentRow--) {
			// check if the target array index is empty
			if (charArray[currentRow][currentColumnIndex] == '\0') {
				nextEmptyRowIndex = currentRow;
				break;
			}
		}
		charArray[nextEmptyRowIndex][currentColumnIndex] = (char) tileType;

		// check matchTile and set the replaceStars flag to false
		// This will replace matching tiles with Stars
		return matchTile(charArray, false);
	}

	/**
	 * Iterate through 2D array and display them in the Grid format
	 */
	private static void displayGrid(char[][] charArray, int rows, int cols) {
		for (int i = 0; i <= rows; i++) {

			if (i == 0) {
				for (int j = 1; j <= cols; j++) {
					if (j == 1)
						System.out.printf("%s" + j, "  ");
					else
						System.out.printf("%s" + j, " ");
				}
			} else {
				for (int j = 0; j <= cols; j++) {
					if (j == 0) {
						System.out.printf("%s|", " ");
					} else {
						if (charArray[i - 1][j - 1] == '\0') {
							System.out.printf("%s|", " ");
						} else {
							System.out.printf("%s|", (char) charArray[i - 1][j - 1]);
						}
					}
				}
			}
			System.out.println();
		}

	}

}
