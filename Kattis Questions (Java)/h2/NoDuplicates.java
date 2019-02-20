import java.io.*;
import java.util.*;

public class NoDuplicates{
	public static void main(String[] args) throws IOException{

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		LinkedList<String> words = new LinkedList<String>();
		Scanner line = new Scanner(in.readLine());
		while(line.hasNext()){
			String word = line.next();
			if(words.contains(word)){
				System.out.println("no");
				System.exit(0);
			}
			words.add(word);
		}
		System.out.println("yes");
	}
}