//V00874629 Knowles, Christian
import java.io.*;
import java.util.*;

public class kornislav{
	public static void main(String args[]){
		Scanner s = new Scanner(System.in);
		int a = s.nextInt();
		int b = s.nextInt();
		int c = s.nextInt();
		int d = s.nextInt();
		int option1 = Math.min(a,b)*Math.min(c,d);
		int option2 = Math.min(a,c)*Math.min(b,d);
		int option3 = Math.min(a,d)*Math.min(b,c);
		if(option1>=option2 && option1>=option3){
			System.out.println(option1);
		}
		else if(option2>=option3 && option2>=option1){
			System.out.println(option2);
		} else {
			System.out.println(option3);
		}
	}
}