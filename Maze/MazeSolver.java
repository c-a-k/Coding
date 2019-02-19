/*
 * MazeSolver.java
 *
 * Christian Knowles
 * March 12, 2017
 * 
 *
 * This class only contains the findPath method, and its sole purpose is
 * to utilize the stack and maze classes to find the solution to any maze
 * This is accomplished through the use of a generic stack and a 2d boolean array
 * to keep track of visited locations.
 *
 */

public class MazeSolver {
    public static String findPath(Maze maze){
    	String result = ""; // start of solution
    	StackRefBased<MazeLocation> tracker = new StackRefBased<MazeLocation>(); //stack for solution
    	tracker.push(maze.getEntry()); //start stack at entrance
    	int row = maze.getEntry().getRow(); // initial row
    	int col = maze.getEntry().getCol(); // initial column
    	boolean [][] bct = new boolean[maze.getNumRows()][maze.getNumCols()];
    	//2D boolean array to track visited locations
    	//bct of course stands for bread crumb trail
    	bct[row][col] = true; // set the entrance as a visited location
    	MazeLocation curr = maze.getEntry(); // set current MazeLocation to be the entrance
    	while(!curr.equals(maze.getExit())){ // while the current MazeLocation is not the exit
    		row = curr.getRow();
    		col = curr.getCol();
    		if(bct[row][col+1] == false && !maze.isWall(row, col+1)){ // if location to right is not a wall and hasn't been visited
    			col++; //move one space right
    			curr = new MazeLocation(row, col); // location is one to the right
    			tracker.push(curr); //push current location onto stack
    			bct[row][col] = true; //set location as visited
    		}
    		else if(bct[row+1][col] == false && !maze.isWall(row+1, col)){ // if location below is not a wall and hasn't been visited
    			row++; //move one space down
    			curr = new MazeLocation(row, col); //location is now one down
    			tracker.push(curr);
    			bct[row][col] = true;
    		}
    		else if(bct[row-1][col] == false && !maze.isWall(row-1, col)){ // if location above is not a wall and hasn't been visited
    			row--; //move on up
    			curr = new MazeLocation(row, col); //location is one up
    			tracker.push(curr);
    			bct[row][col] = true;
    		}
    		else if(bct[row][col-1] == false && !maze.isWall(row, col-1)){ // if location to left is not a wall and hasn't been visited
    			col--; //move one to the left
    			curr = new MazeLocation(row, col); //location is now one left
    			tracker.push(curr);
    			bct[row][col] = true;
    		} else { //if there's nowhere new to go
    			try{
    			tracker.pop(); //pop the element
    			curr = tracker.peek(); //set the current location to the new element
    			if(curr.equals(maze.getEntry())){ //if current is now entrance, then it is guaranteed to have no exit
    				tracker.pop();
    				break; //end the while loop
    			}
    			} catch (StackEmptyException e){
    				break; //end while loop in the event of an exception
    			}
    			
    		}
    	}
    		if(tracker.isEmpty()){
    			return result; //no solution for maze because no exit
    		}
    		int x; // row location
    		int y; // column location
    		MazeLocation reference = new MazeLocation(0,1); //initialized
    		StackRefBased<MazeLocation> reverseStack = new StackRefBased<MazeLocation>();
    		//this new stack will serve as a placeholder to put the stack in order from entrance to exit
    		while(!tracker.isEmpty()){
    			try{
    			reverseStack.push(tracker.pop()); //push the top of the tracker stack onto reverseStack
    			}catch (StackEmptyException e){
    				break;
    			}
    		} 
    		while(!reverseStack.isEmpty()){
    			try{
    			reference = reverseStack.pop(); //reference location is top of stack
    		} catch (StackEmptyException e){
    				break;
    			}
    			x = reference.getRow(); // set x to reference row
    			y = reference.getCol(); // set y to reference column
    			if(reference.getRow() == maze.getEntry().getRow() && reference.getCol() == maze.getEntry().getCol()){
    				result += "(" + x + "," + y + ")"; //case for entrance so that spaces are only in between ordered pairs
    			} else {
    				result += " (" + x + "," + y + ")"; //add row and column as ordered pair for result
    			}

    		}
        return result;
    }
}
