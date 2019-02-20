//V00874629 Knowles, Christian

import java.util.*;
import java.io.*;

public class BaconEggsSpam{

	public static void main(String[] args) throws IOException{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		for(;;){
			int n = Integer.parseInt(in.readLine());
			if(n==0){
				break;
			}
			TreeMap<String, List<String>> orders = new TreeMap<String, List<String>>();
			
			for(int i=0; i<n; i++){
				Scanner line = new Scanner(in.readLine());
				String name = line.next();
				while(line.hasNext()){
					String food = line.next();
					List<String> temp = orders.get(food);
					if(temp == null){
						temp = new ArrayList<>();
						temp.add(name);
						orders.put(food, temp);
					} else {
						orders.get(food).add(name);
					}
				}
			}

			StringBuilder out = new StringBuilder();
			int list = orders.size();
            for(int j=0;j<list;j++){
            	String item = orders.firstKey();
                List<String> l = orders.get(item);
                Collections.sort(l);
                out.append(item + " ");
                for(String s:l){
                    out.append(s + " ");
                }
                out.append("\n");
                orders.remove(item);
            }
			System.out.println(out.toString());
		}
	}
}