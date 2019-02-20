//V00874629 Knowles, Christian

/*

The first move to solve this problem is to create a parent array which corresponds to the parent in
each branch of this tree.  The scanner reads a line of input (after determining the starting point)
and constructs an array where the first element is the parent of all other elements in that array.
The information is then used to fill in the parent array, doing so until all the input is read.
Once all the lines are read, the parent array is used to find a route from the kitten to route by 
jumping from parent to parent.

*/

import java.io.*;
import java.util.*;

public class KittenTree{

	public static void main(String[] args){

		int[] parentArray = new int[101];

		Scanner in = new Scanner(System.in);
		int kittin = Integer.parseInt(in.nextLine()); //found the kittin

		while(true){
		    int[] family = Arrays.stream(in.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		    //puts all the values in the line into an array of integers
		    int parent = family[0];
		    if (parent == -1){
		    	break;
		    } 
		    for(int i=1; i<family.length; i++){
		    	parentArray[family[i]] = parent;
		    	//filling in the parent array using the input
		    } 
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(kittin));
		//add kittin
		    kittin = parentArray[kittin];
		        while(kittin>0){
		            sb.append(' ');
		            sb.append(Integer.toString(kittin));
		            //insert parent of current value
		            kittin = parentArray[kittin];
		            //set current value to its parent
		        }
		System.out.println(sb.toString());
	}
}