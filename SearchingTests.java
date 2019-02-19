//Christian Knowles
//V00874629
//Lab Assignment 9
//March 21, 2017

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class SearchingTests {

	private static final String border = "*****************************************";
	ArrayList<Integer> list;

	 void primeList(int numElements) {
		Random r = new Random();
		list = new ArrayList<Integer>(numElements);
		for (int i=0; i<numElements; i++) {
			list.add(r.nextInt(numElements));
		}
		Collections.sort(list);
	}

	// Search for the target.
	// return the number of comparisons done.
	int linearSearch(Integer target) {
		int count = 0;
		for(Integer i: list){
			count++;
			if(i.equals(target)){
				break; //number of comparisons = index + 1
			}
		}
		return count;
	}

	// Search for the target
	// return the number of comparisons done.
	int binarySearch(Integer target) {
		int result = bS(0,list.size()-1, target);
		return result;
	}

	int bS(int start, int end, Integer target){
		if((end-start) == 0){
			return 0;
		}
		if((end-start) == 1){
			return 1;
		}
		int mid = (end+start)/2;
		int where=target.compareTo(list.get(mid));
		if(where == 0){
			return 1;
		}
		else if(where>0){
			return 1+bS(mid+1, end, target);
		} else {
			return 1+bS(start, mid-1, target);
		}
	}

	// sample test.
	public static void main(String[] args) {
		int numElements = 1000;
		int numTests = 100;

		SearchingTests test = new SearchingTests();
		test.primeList(numElements);
		Random rand = new Random();
		long linearSearchTotal = 0;
		long binarySearchTotal = 0;
		int target;
		for (int i=0; i<numTests; i++) {
			target = rand.nextInt(numElements);
			linearSearchTotal += test.linearSearch(target);
			binarySearchTotal += test.binarySearch(target);
		}
		System.out.println(border);
		System.out.println("Average number of comparisions for list of size "+numElements+":");
		System.out.printf("%-10s%-10s%-10s\n","Tests","Linear","Binary");
		System.out.printf("%-10s%-10s%-10s\n",numTests,linearSearchTotal/numTests,binarySearchTotal/numTests);
	}
} 
