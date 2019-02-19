/*
Christian Knowles
November 20th, 2016
*/

/*
A common practice in many games is to ask the player if they want to try again ater wither winning or losing
I decided to apply the same idea in this program by creating a prompt and using a bit of code to either terminate the 
program (using System.exit(0)) or restart the program (using main(args)).  This would allow the user to comfortably
play the game over and over again.  See lines 55-70 for extra work.
*/

import java.util.*;
public class MineSweeperExtra {
	public static void main(String []args){
		char[][] fullGrid = initializeFullGrid();
		//grid is initialized as a 2D character array based on the method
		char[][] gameGrid = new char[8][8];
		//respresents game grid in its current state
		for(int i=0; i<gameGrid.length; i++){
			Arrays.fill(gameGrid[i],'.'); //fills the array with '.' characters
		}
		Scanner in = new Scanner(System.in);
		int row; //row number
		int col; //column number
		int remainingDots = remainingCells(gameGrid);
		while(remainingDots > 0){
			//while there are still cells to be uncovered
			drawFullGrid(gameGrid);
			System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
			row = in.nextInt();
			while(row<0 || row>7){ //making sure the user entered a number within range
				System.out.println("Out of bounds!  Try again.");
				System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
				row = in.nextInt();
			}
			System.out.print("Select a cell. Column value (a digit between 0 and 7): ");
			col = in.nextInt();
			while(col<0 || col>7){ //making sure the user entered a number within range
				System.out.println("Out of bounds!  Try again.");
				System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
				col = in.nextInt();
			}
			revealGridCell(row, col, fullGrid, gameGrid);
			//edits game board based on user input
			remainingDots = remainingCells(gameGrid);
			//method is used to determine the number of available cells on the grid
		}
		if(remainingDots == 0){ //when all the non bomb cells are revealed
			System.out.println("Congrats! You Won!");
			drawFullGrid(fullGrid); //prints unocvered board to show all bombs
			System.out.print("Play Again? (Y/N) "); //asks user to play again
			String response = in.next();
			if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("Yes")){ //if response is yes
				main(args); //restart the program
			} else {
				System.exit(0); //terminate the program
			}
		} else { //when a bomb cell is revealed
			System.out.println("Kaboom! Game Over!");
			drawFullGrid(fullGrid); //prints unocvered board to show all bombs
			System.out.print("Play Again? (Y/N) "); //asks user to play again
			String response = in.next();
			if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("Yes")){ //if response is yes
				main(args); //restart the program
			} else {
				System.exit(0); //terminate the program
			}
		}
	}
	public static char[][] initializeFullGrid(){
		char[][] fullGrid = new char[8][8];
		for(int i=0; i<fullGrid.length; i++){
			Arrays.fill(fullGrid[i],'0');
			//for the purposes of incrementation the array values are all '0'
		}
		int bRow; //initialize variable for row of random bomb
		int bColumn; //initlize variable for column of random bomb
		Random bombs = new Random();
		for(int i=0; i<10; i++){
			//places ten bombs
			bRow = bombs.nextInt(8);
			bColumn = bombs.nextInt(8);
			while(fullGrid[bRow][bColumn] == 'B'){
				//while loop to make sure bombs are in a unique coordinate
				bRow = bombs.nextInt(8);
				bColumn = bombs.nextInt(8);
			}
			fullGrid[bRow][bColumn] = 'B';
		}
		for(int i=0; i<fullGrid.length; i++){
			for(int j=0; j<fullGrid[i].length; j++){
				//fill in the numbers for the grid;
				if(fullGrid[i][j] == 'B'){
					if(i == 0){ //top
						if(j == 0){ //top left
							fullGrid[i+1][j]++; //space below
							fullGrid[i][j+1]++; //space to the right
							fullGrid[i+1][j+1]++; //bottom right space
						} 
						else if(j == fullGrid[0].length-1){ //top right
							fullGrid[i+1][j]++; //space below
							fullGrid[i][j-1]++; //space to the left
							fullGrid[i+1][j-1]++; //bottom left space
						} 
						else { //the other top ones
							fullGrid[i+1][j]++; //space below
							fullGrid[i][j+1]++; //space to the right
							fullGrid[i+1][j+1]++; //bottom right space
							fullGrid[i][j-1]++; //space to the left
							fullGrid[i+1][j-1]++; //bottom left space
						}
					}
					else if(i == fullGrid.length - 1){ //bottom
						if(j == 0){ //bottom left
							fullGrid[i-1][j]++; //space above incremented
							fullGrid[i][j+1]++; //space to the right
							fullGrid[i-1][j+1]++; //top right space
						} 
						else if(j == fullGrid[fullGrid.length - 1].length-1){ //bottom right
							fullGrid[i-1][j]++; //space above incremented
							fullGrid[i-1][j-1]++; //top left space
							fullGrid[i][j-1]++; //space to the left
						} 
						else { //the other bottom ones
							fullGrid[i-1][j]++; //space above incremented
							fullGrid[i][j+1]++; //space to the right
							fullGrid[i-1][j+1]++; //top right space
							fullGrid[i-1][j-1]++; //top left space
							fullGrid[i][j-1]++; //space to the left
						}
					}
					else if(j == 0){ //left side
						fullGrid[i-1][j+1]++; //top right space
						fullGrid[i][j+1]++; //space to the right
						fullGrid[i+1][j+1]++; //bottom right space
						fullGrid[i-1][j]++; //space above
						fullGrid[i+1][j]++; //space below
					}
					else if(j == fullGrid[i].length-1){ //right side
						fullGrid[i-1][j-1]++; //top left space
						fullGrid[i][j-1]++; //space to the left
						fullGrid[i+1][j-1]++; //bottom left space
						fullGrid[i-1][j]++; //space above
						fullGrid[i+1][j]++; //space below
					} 
					else {
						fullGrid[i-1][j-1]++; //top left space
						fullGrid[i-1][j]++; //space above
						fullGrid[i-1][j+1]++; //top right space
						fullGrid[i][j-1]++; //space to the left
						fullGrid[i][j+1]++; //space to the right
						fullGrid[i+1][j-1]++; //bottom left space
						fullGrid[i+1][j]++; //space below
						fullGrid[i+1][j+1]++; //bottom right space
					}
				}
				for(int k=0; k<fullGrid.length; k++){
					//i is within the scope so I used 'k' instead
					for(int l=0; l<fullGrid[k].length; l++){
						//same with 'j'
						if(fullGrid[k][l] == 'C'){
							fullGrid[k][l] = 'B';
						}
					}
				}
				//I realized that my incrementation loop changes the character 'B' to 'C' so I'm using a loop to change it back
			}
		}
		for(int k=0; k<fullGrid.length; k++){
			//i is within the scope so I used 'k' instead
			for(int l=0; l<fullGrid[k].length; l++){
				//same with 'j'
				if(fullGrid[k][l] == '0'){
					fullGrid[k][l] = ' ';
					}
				}
			}
		return fullGrid;
	}
	public static void revealGridCell(int row, int col, char[][] fullGrid, char[][] gameGrid){
		gameGrid[row][col] = fullGrid[row][col];
		if(gameGrid[row][col] == 'B'){
			fullGrid[row][col] = 'X'; //this will show where the bomb was hit in the grid
		}
		if(gameGrid[row][col] == ' '){
			if(row == 0){ //top
				if(col == 0){ //top left corner
					gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
					gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
					gameGrid[row+1][col+1] = fullGrid[row+1][col+1]; //space down 1 and right 1
				}
				else if(col == gameGrid.length-1){ //top right corner
					gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
					gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
					gameGrid[row+1][col-1] = fullGrid[row+1][col-1]; //space down 1 and left 1
				} else { //top but not corner
					gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
					gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
					gameGrid[row+1][col+1] = fullGrid[row+1][col+1]; //space down 1 and right 1
					gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
					gameGrid[row+1][col-1] = fullGrid[row+1][col-1]; //space down 1 and left 1
				}
			}
			else if(row == gameGrid.length-1){ //bottom
				if(col == 0){ //bottom left corner
					gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
					gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
					gameGrid[row-1][col+1] = fullGrid[row-1][col+1]; //space up 1 and right 1
				}
				else if(col == gameGrid.length-1){ //top right corner
					gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
					gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
					gameGrid[row-1][col-1] = fullGrid[row-1][col-1]; //space up 1 and left 1
				} else { //top but not corner
					gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
					gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
					gameGrid[row-1][col+1] = fullGrid[row-1][col+1]; //space up 1 and right 1
					gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
					gameGrid[row-1][col-1] = fullGrid[row-1][col-1]; //space up 1 and left 1
				}
			}
			else if(col == 0){ //left not including corners
				gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
				gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
				gameGrid[row-1][col+1] = fullGrid[row-1][col+1]; //space up 1 and right 1
				gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
				gameGrid[row+1][col+1] = fullGrid[row+1][col+1]; //space down 1 and right 1
			}
			else if(col == gameGrid[row].length-1){ //right not including corners
				gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
				gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
				gameGrid[row-1][col-1] = fullGrid[row-1][col-1]; //space up 1 and left 1
				gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
				gameGrid[row+1][col-1] = fullGrid[row+1][col-1]; //space down 1 and left 1
			} else {
				gameGrid[row-1][col] = fullGrid[row-1][col]; //space above
				gameGrid[row][col-1] = fullGrid[row][col-1]; //space to the left
				gameGrid[row-1][col-1] = fullGrid[row-1][col-1]; //space up 1 and left 1
				gameGrid[row+1][col] = fullGrid[row+1][col]; //space below
				gameGrid[row+1][col-1] = fullGrid[row+1][col-1]; //space down 1 and left 1
				gameGrid[row][col+1] = fullGrid[row][col+1]; //space to the right
				gameGrid[row-1][col+1] = fullGrid[row-1][col+1]; //space up 1 and right 1
				gameGrid[row+1][col+1] = fullGrid[row+1][col+1]; //space down 1 and right 1
			}
		}
	}
	public static void drawFullGrid(char[][] fullGrid){
		System.out.print("  |");
		for(int i=0; i<fullGrid[0].length; i++){
			System.out.print(" " + i);
			//creates first line of board
		}
		System.out.println();
		for(int i=0; i<3 + 2*fullGrid[0].length; i++){
			System.out.print("-");
			//creates top line of dashes
		}
		System.out.println();
		for(int i=0; i<fullGrid.length; i++){
			System.out.print(i + " |");
			for(int j=0; j<fullGrid[i].length; j++){
				System.out.print(" " + fullGrid[i][j]);
				//prints each row and column of the array
			}
			System.out.println();
		}
	}
	public static int remainingCells(char [][] gameGrid){
		int remainingDots = 54;
		//number of cells in board (64) minus number of bombs (10)
		for(int i=0; i<gameGrid.length; i++){
			for(int j=0; j<gameGrid[i].length; j++){
				if(gameGrid[i][j] == 'B'){
					//checks game grid for revealed bombs
					remainingDots = -1;
					//if one is found the variable will be set to -1, thus terminating the while loop in the main
					return remainingDots;
				}
				if(gameGrid[i][j] != '.'){
					remainingDots--;
					//variable subtracts one for each revealed cell
				}
			}
		}
		return remainingDots;
		//returns number of remaining cells uncovered
	}
}