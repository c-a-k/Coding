//V00874629 Knowles, Christian

import java.io.*;
import java.util.*;

/*

Efficiently grabbing a cookie from the middle is possible if two priority queues are used,
with the head of each corresponding to the one of two cookies in the middle.  The cookie
is scanned and inserted into the lower half (head is largest value) or upper half (head is
smallest value of queue).  When a call is made to extract a cookie, the cookie will come
from up top if there is an even number of cookies and down low if there's an off number.
When a cookie is added or removed, the lists will be balanced in order to properly
represent the middle of the tray.

*/

public class CookieSelect {
	public static void main(String args[]) throws IOException{

		Scanner in = new Scanner(System.in);

		PriorityQueue<Integer> upTop = new PriorityQueue<Integer>();
		PriorityQueue<Integer> downLow = new PriorityQueue<Integer>(1, Collections.reverseOrder());
		//this priority queue has the max on its head^^^

		String input;
		int freshCookie;
		int balance;

		while(in.hasNext()){

			input = in.nextLine();

			//take a cookie
			if(input.equals("#")){
				if(upTop.size()>=downLow.size()){
					System.out.println(upTop.poll());
				} else {
					System.out.println(downLow.poll());
				}
			//give a cookie
			} else {
				freshCookie = Integer.parseInt(input);
				if(downLow.size()==0){
					downLow.add(freshCookie);
				}
				else if(freshCookie<=downLow.peek()){
					downLow.add(freshCookie);
				} else {
					upTop.add(freshCookie);
				}
			}
			//balance the two lists correctly
			balance = upTop.size()-downLow.size();
			if(balance>1){
				downLow.add(upTop.poll());
			}
			if(balance<-1){
				upTop.add(downLow.poll());
			}
		}
	}
}