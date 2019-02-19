/*
Christian Knowles
V00874629
CurrencyToWords.java
*/
/*
CurrencyToWords is a program designed to output a line of output exactly 70 characters long
that displays a monetary value (inputted by the user) into words and numbers that resemble a cheque
This program was created with the use of while loops, multiway if else statements, arrays of strings, and logical expressions
The output varies according to the value inputted by the user, and accounts for all cases between 0 and 10000
as well as deals with exceptions to that range of values.
*/
import java.util.*;
//allow for the scanner class
public class CurrencyToWords{
	public static void main(String [] args){
        //the main simply contains method calls and output the final product.  See the actual methods for details on the processes of getting there.
		double value = getValueFromUser();
        //a value is created equal to the value entered by the user (see getValueFromUser method for more details)
		String text = convertToWords(value);
        //a string is created equal to the string returned in the method called
		System.out.println(text);
        //the string is outputted
	}
	public static String baseCardinalToString(int value){
        /*
        This method specifically deals with converting the dollar amount inputted by the user into a string
        I used string arrays to represent the values of the digits.  They are divided in tens and values less than twenty.
        Since the output is designed to include "-" as well "and" when certain values exist, many cases had to be made
        to account for the permutations in zero and nonzero digits.  Using modulo multiple times, the correct digit could be exracted
        in every case necessary to determine which case should run and which string in the array should be used.
        */
		String valueInWords = "";
        //string is initizalized as nothing
		if (value == 0){
			return "zero";
            //special case for value = 0 (minimum)
		}
		if (value == 10000){
			return "ten thousand";
            //special case for value = 10000 (maximum)
		}
		String ones[] = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "ninteen"
            }; //an array of strings is created from one to nineteen
        String tens[] = {"", "", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        //an array of strings is created from twenty to ninety
        if (value > 999){
            //between 1000 and 9999
        	valueInWords = ones[value / 1000] + " thousand";
        	if (value % 1000 != 0){
        		//if any other digit is not zero
        		if (value / 100 % 10 != 0){
        		//if the hundreds digit is not zero
        		valueInWords += " " + ones[value / 100 % 10] + " hundred";
        		//the hundreds digit is extracted and a string is printed that corresponds with the value of the digit
        		}
        		if (value / 10 % 10 % 10 >= 2 && value % 100 % 10 % 10 != 0){
        		//if the tens digit is 2 or greater and the ones digit is not zero
        		valueInWords += " and " + tens[value / 10 % 10 % 10] + "-" + ones[value % 100 % 10 % 10];
        		//the rest of the string will format accordingly to the values of the tens and ones
        		}
        		else if (value / 10 % 10 % 10 >= 2 && value % 100 % 10 % 10 == 0){
        		//if the value of the ten digit is greater than one and the ones digit is zero
        		valueInWords += " and " + tens[value / 10 % 10 % 10];
        		//the tens digit will be added in words to the string
        		} 
        		else if (value % 100 != 0) {
        		//if the tens digit is less than two
        		valueInWords += " and " + ones[value % 100];
        		//a value less than 20 will be printed in words
        		}
        	}
    	}
    	else if (value > 99){
            //between 100 and 999
        	valueInWords += ones[value / 100] + " hundred";
        	if (value / 10 % 10 >= 2 && value % 10 != 0){
        		//if the tens digit is 2 or greater and the ones digit is not zero
        		valueInWords += " and " + tens[value / 10 % 10] + "-" + ones[value % 10 % 10];
        		//the rest of the string will format accordingly to the values of the tens and ones
        		}
        		else if (value / 10 % 10 >= 2 && value % 10 == 0){
        		//if the value of the ten digit is greater than one and the ones digit is zero
        		valueInWords += " and " + tens[value / 10 % 10];
        		//the tens digit will be added in words to the string
        		}
                else if (value % 100 != 0) {
        		//if the tens digit is less than two and the two digits aren't zero
        		valueInWords += " and " + ones[value % 100];
        		//a value less than 20 will be printed in words
        		}
        	}
        else if (value > 19){
             //between 20 and 99
            if (value % 10 == 0){
                //if the ones digit is zero
                valueInWords = tens[value / 10];
                //a string of the tens array will become valueInWords depending on value
            } else {
                valueInWords = tens[value / 10] + "-" + ones[value % 10];
                //a string of the tens array and ones array will become valueInWords depending on value
            }
        } else {
            //between 1 and 19
            valueInWords = ones[value];
            //valueInWords becomes a string based on the value of itself
        }
        return valueInWords;
        //end method :)
	}
	public static String convertToWords(double value){
        /*
        This method weaves everything together into a single string.
        The method baseCardinalString is used for the first part to display the dollar value in words.
        A for loop is used to print dashes so that the length of the entire string is 70.
        The last part of the string displays the value of cents and a string combines these three components and returns the string.
        */
		int intValue = (int) value;
        //takes the user value and truncates the decimal
		String dollarAmount = baseCardinalToString(intValue);
        //string dollarAmount is equal to the user value in words (see baseCardinalToString method)
        int cents = ((int)(value * 100.0)) % 100;
        //decimal is extracted from the user value
        String centsDollars = cents + "/100 dollars";
        //last component of output is created (number of cents and dollars)
        String dash = "";
        //creates a String with no value
        for (int i = 1; i <= 70 - centsDollars.length() - dollarAmount.length() - 2; i++){
            //for loop is created and runs based on the length of the value in words and last component (number of cents and word 'dollars')
            dash += "-";
            //every time the loop runs another dash is added to the string
        }
        dash = " " + dash + " ";
        // a space is adde before and after the string for formatting purposes
        String fullOutput = dollarAmount + dash + centsDollars;
        //the three components are added to create the complete string
		return fullOutput;
        //this string is returned and called back in the main method
	}
	public static double getValueFromUser(){
        /*
        This method is simply designed to ask the user for a monetary value between 0 and 10000.
        A while loop is used so that the user has multiple attempts to type in the correct input.
        When a valid input is entered, it is assiged to a variable and returned as a double to be used in other methods.
        */
		Scanner s = new Scanner(System.in);
        //A new scanner s is created
		System.out.print("What is the monetary amount? ");
        //program prompts user to enter a monetary amount
		double monetaryValue = s.nextDouble();
        //the value inputted is stored as a double to account for decimals (cents)
		while (monetaryValue < 0 || monetaryValue > 10000){
            //if the monetary value is out of the range of 0 and 10000, the program will not return a value
            //the loop will continue until te user enters a valid input
			System.out.println("please enter a valid input.");
            //user is reminded to enter a valid input
			System.out.println("The value should between zero and ten thousand.");
            //the program specifies that the value should be zero and ten thousand
			monetaryValue = s.nextDouble();
            //the value inputted by user will be tested to see if the loop continues
		}
		return monetaryValue;
        //when a valid input is entered, the value is returned and the method ends
	}
}