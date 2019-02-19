/*
 * Christian Knowles 
 * September 29th, 2016
 * EagleSpeaks.java
 */
/* EagleSpeaks was designed to allow the user of this program to input a line of text
 * and have the ascii eagle make that text appear in a box that adjusts to the length of the user's input.
 * By using for loops and scanner objects, the program is able to print out any string (inlcuding whitespace)
 * and have it outputted in a text box that varies with respect to the string output.
 */
import java.util.*;
public class EagleSpeaks{
	public static void main(String[] args){
		//all the important statements of this program are in the speak and printEagle method
		speak();
	}
	public static void printEagle(){
		//A few modifications were made, but overall the eagle's design is the same as in the first assignment
		System.out.println("    \\             /*/");
		System.out.println("     \\\\\\' ,      / //");
		System.out.println("      \\\\\\//    _/ //'");
		System.out.println("       \\_-//' /  //<'");
		System.out.println("        \\*///  <//'");
		System.out.println("        /  >>  *\\\\\\`");
		System.out.println("       /,)-^>>  _\\`");
		System.out.println("       (/   \\\\ / \\\\\\");
		System.out.println("\t    //  //\\\\\\");
		System.out.println("      /    ((");
		System.out.println("     /");
	}
	public static void speak(){
		/*
		* Everything in the main method is contained in this method
		* All the for loops and objects are in the same method so that the variables could communicate with one another.
		* I tried to have separate methods for the for loops, but due to the scope of the variables, it wouldn't work properly.
		*/
		Scanner talk = new Scanner(System.in);
		//porgram prompts user to enter string
		System.out.print("What would you like the eagle to say? ");
		//String s is established as the line of user input
		String s = talk.nextLine();
		//varible int x is declared as string's length for the for loop
		int x = s.length();
		//the previous method for the ascii eagle is called now so that the input is obtained before the eagle appears to speak
		printEagle();
		System.out.print("*");
		//the dashes that form the top and bottom of the box vary depending on the length of x + 2
		for (int y = 1; y <= x + 2; y++){
			System.out.print("-");
		}
		//user input is outputted in the middle of the box along with a space before and after for formatting
		System.out.print("*\n| " + s + " |\n*");
		//for loop is repeated
		for (int y = 1; y <= x + 2; y++){
			System.out.print("-");
		}
		System.out.println("*");
	}
}