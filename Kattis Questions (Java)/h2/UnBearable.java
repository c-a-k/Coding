import java.io.*;
import java.util.*;

public class UnBearable{
	public static void main(String[] args) throws IOException{
		int ListNo = 1;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for(;;){
			int numOfAnimals = Integer.parseInt(in.readLine());
			if(numOfAnimals==0){
				break;
			}
			TreeMap<String, Integer> animals = new TreeMap<String, Integer>();
			for(int j=0; j<numOfAnimals; j++){
			Scanner cage = new Scanner(in.readLine());
		while(cage.hasNext()){
			String word = cage.next();
			if(!cage.hasNext()){
				String bear = word.toLowerCase();
				if(animals.containsKey(bear)){
					int replace = animals.get(bear);
					replace++;
					animals.remove(bear);
					animals.put(bear, replace);
				} else {
					animals.put(bear, 1);
				}
			}
		}
	}
		
		StringBuilder sb = new StringBuilder();
		sb.append("List " + ListNo + ":\n");
		while(animals.size()>0){
			int num = animals.get(animals.firstKey());
			String s = animals.firstKey();
			animals.pollFirstEntry();
			sb.append(s + " | " + num + "\n");
		}
		System.out.print(sb.toString());
		ListNo++;
		} //end of for loop
	}
}