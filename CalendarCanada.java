/*
 * Christian Knowles 
 * October 9th, 2016
 * CalendarCanada.java
 */
/* 
 * CalendarCanada is a program designed to print a calendar with different numbers of days,
 * as well as different staring dates (Monday, Tuesday, etc.), depending on user input.
 * This program was made with the help of nested for loops, if else statements, scanner and string objects.
 */
import java.util.*;
//allows Scanner objects to be used
public class CalendarCanada{
	public static void main(String[] args){
		/*
		 * The main method contains method calls to three other methods in the program.
		 * The main creates a scanner object for user input, and formats the method calls,
		 * so that the output is proper.
		 */
		Scanner s = new Scanner(System.in);
		//creates a new scanner for user input
		System.out.print("Number of days in month? ");
		//asks user how many days in the month for the calendar
		int numDays = s.nextInt();
		//the program saves this input as a variable numDays
		System.out.print("Date of first Sunday? ");
		//asks user the date of the first Sunday of the month
		int startingSunday = s.nextInt();
		//the program saves this response as variable startingSunday
		System.out.println("   Su    Mo    Tu    We    Th    Fr    Sa");
		//prints the days of the week above the calendar
		printSeparator();
		//calls the method printSeparator
		outputCalendar(numDays, startingSunday);
		//the method outputCalendar is called with the parameters of the user input
		printSeparator();
		//calls printSeparator once more
	}
	public static void printSeparator(){
		/*
		 * The printSeparator method serves to output the bottom and top of the calendar
		 */
		System.out.print("+");
		//the method starts with a + character for foratting
		for (int i=1; i<=7; i++){
			System.out.print("-----+");
			//the output is printed seven times, one for each day of the week
		}
		System.out.println();
		//the program moves to the next line
	}
	public static String rightJustify(int value, int spaces){
		/*
		 * This String method corresponds to one numerical day of the month.
		 * A string is created and, depending on the number of digits in a day,
		 * adds spaces to the string until it reaches a certain string length.
		 * At the end, a vertical line and space are added to finish things off.
		 */
		String days = " " + value;
		//A string is created with a space and a value
		for (int j = days.length(); j < spaces; j++){
			days = " " + days;
			//the loop will add a space to the string until it reaches the value of the spaces parameter
		}
		days = "|" + days + " ";
		//a space at the end and a vertical line at the beginning of the string to finish the string
		return days;
		//this value will be returned and called in the outputCalendar method
	}
	public static void outputCalendar(int numDays, int startingSunday){
		/*
		 * This method outputs the days and blank boxes in the calendar.
		 * This method conatains three for loops, one of which is a nested for loop.
		 * This method also uses a combination of if else statements to output blank boxes and boxes with dates on it.
		 */
		if(startingSunday!=1){
			//This loop will only run if the 1st is not a Sunday
		for (int i=startingSunday-7; i<startingSunday; i++){
			//this loop will run seven times, and the variable i is initialized with the value of the first Sunday minus 7
			if(i<1){
				//if i is a negative integer or zero, this statement will execute
				System.out.print("|     ");
				//a blank space is printed
			} 
			else{
				//if i is a positive integer
				System.out.print(rightJustify(i,4));
				//the method rightJustify is called and returns a box will value i inside
			}
		}
		System.out.println("|");
		//the row of dates ends with a vertical line and moves to the next line
		}
		int middleNums = startingSunday;
		//a variable middleNums is declared and initialized as the value of startingSunday
		//it is declared outside the for loop so that it can be used continuously in all for loops
		for (int i=1; i<=(numDays-startingSunday)/7; i++){
			//this for loop will run depending on how many days are in the month and the date of the first Sunday
			for (int j = 1; j<=7 ; j++){
				//the for loop is run seven times
				System.out.print(rightJustify(middleNums,4));
				//the method rightJustify is called with the same space width as the row above and the value of middleNums
				middleNums += 1;
				//middleNums is iterated by one so that each box produces the next day of the month
			}
			System.out.println("|");
			//the for loop ends with a vertical line and shifts the output to the next row
		}
		for (int i=1; i<=7; i++){
			//this for loop is run seven times
			if(middleNums<=numDays){
				//if the days outputted are not greater than the days of the month
				System.out.print(rightJustify(middleNums,4));
				//rightJustify is outputted with the value middleNums
				middleNums = middleNums + 1;
				//middleNums is iterated by one so that each box produces the next day of the month
			}
			else{
				System.out.print("|     ");
				//the rest of the for loop prints out an empty box
			}
		}
		System.out.println("|");
		//a vertical line is printed one last time and the calendar rows are complete
	}
}
//end of program :)