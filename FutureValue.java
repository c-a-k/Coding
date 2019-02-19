/*
 * Christian Knowles 
 * September 29th, 2016
 * FutureValue.java
 */
/*
 * FutureValue is a program designed designed to take the user's amount of money they input,
 * the interest rate in percent, and the number of years the interest accrues and prints a year by year
 * breakdown of how much money they have after each year.  Using for loops, if-else statements, and string objects,
 * I was able to display the outputs in a readable format and properly calculate the interest year by year.
 */
import java.util.*;
//allows the scanner object to be used
import java.text.*;
//allows the Decimal format object to be used
public class FutureValue{
	public static void main(String[] args){
		//syntax for main method
		Scanner s = new Scanner(System.in);
		//creates a new scanner object with system input called s
		System.out.print("The starting amount: ");
		double initialValue = s.nextDouble();
		//asks user to imput starting amount
		System.out.print("The interest rate in percent: ");
		double interestMultiplier = 1 + (s.nextDouble() / 100.0);
		//asks user to input interest rate in percent.
		//it is then calculated as a multiplier for the amount
		System.out.print("The number of years: ");
		int years = s.nextInt();
		//asks user to input the number of years the interest builds over time
		System.out.println("Y  $");
		for(int i=0; i<=years; i++){
			//creates a for loop where the statement repeats based on the number of years
			DecimalFormat formatter = new DecimalFormat(".00");
			//creates a formatter to truncate the decimals in the double with the exception of the first two
			double finalValue = initialValue * Math.pow(interestMultiplier,i);
			//calculates the interest multiplier to the power of i
			//this is done so the interest rate is properly calculated each year
			String roundedFinalValue = formatter.format(finalValue);
			//rounds the final value to two decimal places
			if(i<10){
				System.out.println(i + "  " + roundedFinalValue);
				//when i is less than 10, an extra space is added after i so the final values line up correctly
			}else{
				System.out.println(i + " " + roundedFinalValue);
				//when i is equal or greater than 10 (2 digit number), only one space is needed for formatting
			}
		}
	}
}