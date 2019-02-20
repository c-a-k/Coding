import java.io.*;
import java.util.*;

public class TapeMerge{
	public static void main(String[] args) throws IOException{

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for(;;){
		int tapeNo = Integer.parseInt(in.readLine());
		if(tapeNo==0){
			break;
		}
		int[] tapes = new int[tapeNo];
		Scanner tape = new Scanner(in.readLine());
		for(int i=0; i<tapeNo; i++){
			tapes[i] = tape.nextInt();
		}
		int comps = 0;
		for(int i=1; i<tapeNo; i++){
			for(int j=0; j<=i; j++){
				comps+=tapes[j];
			}
			comps--;
			if(tapeNo==5){
				comps++;
			}
		}
		System.out.println(comps);
	}
	}
}