/*
Christian Knowles
November 4th, 2016
*/
/*
Text Analysis is a program that scans a txt file entered by the user and prints out certain
statistics about the text file including the longest word, the frequency of word lengths, 
the number of unique words, the number of words in the txt file, and a word dump of every
unique word and its frequency.  This is achieved using file scanners, one dimensional arrays,
while loops, for loops, if and if-else statements, and string objects.
*/

import java.io.*;
import java.util.*;
//import Scanner class

public class TextAnalysis{
	public static void main(String[] args)
						throws FileNotFoundException,
						ArrayIndexOutOfBoundsException {
		/*
		The main method contain the assembly of the arrays using a complex for loop algorithm
		and the file is read and scanned in the main method.  The main catches any FileNotFoundException and prompts
		the user to enter a file that exists in the same pathway as the program.
		The main also prints out the text for the statistics as well as calls the methods for the statistics.
		*/
		String fileName = args[0];
		//sets string to name of file
		File goodFile = new File(fileName);
		//creates a new file
		Scanner fileReader = null;
		try {
		fileReader = new Scanner(goodFile);
		//creates a scanner to scan the elements of the file
		}
		catch (FileNotFoundException e) {
			//if file does not exist
			while(!goodFile.exists()){
				fileReader = new Scanner(System.in);
				System.out.print("Where's the file man?  Please try again: ");
				fileName = fileReader.nextLine();
				goodFile = new File(fileName);
			}
		}
		String[] wordList = new String[100000];
		//a reference array of 100000 null strings is made
		int[] wordCount = new int[100000];
		//a reference array of 100000 integers is made to correspond to the word count of each unique word
		String[] allWords = new String[100000];
		int indexReference = 0; //reference index for each unique word
		int wordsInFile = 0;
		//counts number of words in file
		while(fileReader.hasNext()){
			//while there are tokens to be scanned
			String tokenScanned = fileReader.next();
			//this string will be equal to the token scanned
			for(int i=0; i<=100000; i++){
				if(wordList[i] == null){
					wordList[i] = tokenScanned;
					indexReference++;
					wordCount[i]++;
					break;
				} else if(tokenScanned.equalsIgnoreCase(wordList[i])){
					wordCount[i]++;
					break;
				}
			}
			wordsInFile++;
			allWords[wordsInFile] = tokenScanned;
		}
		String[] wordListGood = Arrays.copyOfRange(wordList, 0, indexReference);
		int[] wordCountGood = Arrays.copyOfRange(wordCount, 0, indexReference);
		String[] allWordsGood = Arrays.copyOfRange(allWords, 1, wordsInFile+1);
		System.out.println("TEXT FILE STATISTICS");
		System.out.println("--------------------");
		longestWord(wordListGood);
		System.out.println("Number of words in file wordlist: " + indexReference);
		System.out.println("Number of words in file: " + wordsInFile);
		System.out.println();
		wordFrequency(allWordsGood);
		System.out.println();
		wordListDump(wordCountGood, wordListGood);
	}
	public static void longestWord(String[] wordList){
		/*
		longestWord takes a string array as a parameter and determines the longest word
		in that array of strings using a for loop.  A String longestWord is compared to the elemets of the
		array, and if the element is greater in length than longestWord, that element becomes longestWord.
		*/
		int longWordLength = 0;
		String longestWord = "";
		for(int i = 0;i<wordList.length;i++){
			String elementOfWord = wordList[i];
			if(elementOfWord != null && elementOfWord.length()>longWordLength){
				longWordLength = elementOfWord.length();
				longestWord = elementOfWord;
			}
		}
		System.out.println("Length of longest word: " + longWordLength + " (\"" + longestWord + "\")");
	}
	public static void wordFrequency(String[] wordList){
		/*
		wordFrequency is a method that creates an int array representing all the different word lengths from 1 to 10+.
		The length of each element in the string array parameter is checked for, and one is added to the corresponding int element
		equal to the length of the string element.  So a string element of length 6 will add one to the int element at index 6, and the
		final results will be printed to the console.
		*/
		int[] wordLengths = new int[11];
		String wordReference = "";
		for(int i = 0; i<wordList.length; i++){
			wordReference = wordList[i];
			if(wordReference != null){
			if(wordReference.length()>10){
				wordLengths[10]++;
			} else {
			wordLengths[wordReference.length()]++;
			}
		}
		}
		System.out.println("Word-frequency statistics");
		for(int i = 1; i<10; i++){
			System.out.println("  Word-length " + i + ": " + wordLengths[i]);
		}
		System.out.println("  Word-length >= 10: " + wordLengths[10]);
	}
	public static void wordListDump(int[] wordCount, String[] wordList){
		/*
		wordListDump uses a for loop to print each unique word in the text file
		and the number of times that word has been used in the text file.
		*/
		for(int i=0; i<wordCount.length; i++){
			String s = wordList[i];
			String q = s.toLowerCase();
			System.out.println(q + ":" + wordCount[i]);
		}
	}
}