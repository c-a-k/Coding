//V00874629 Knowles, Christian

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.HashMap;

public class Fun{
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(in.ready()){
			boolean notFun = false; //not a function
  			boolean inj = true; //injective function
			boolean surj = true; //surjective function
			String line = in.readLine();
			Scanner s = new Scanner(line);
			s.next();
			String[] domain = s.nextLine().trim().split(" "); //set up domain values
			line = in.readLine();
			s = new Scanner(line);
			s.next();
			String[] codomain = s.nextLine().trim().split(" "); //set up codomain values
			HashMap<String,String> mappings = new HashMap<String,String>();
			int numOfMappings = Integer.parseInt(in.readLine());
			for (int i=0; i<numOfMappings; i++){
				line = in.readLine();
				Scanner mapping = new Scanner(line);
				String x = mapping.next();
				mapping.next(); //skip the ->
				String y = mapping.next();
				if(mappings.containsKey(x)){
					notFun = true;
				}
				if(mappings.containsValue(y)){
					inj = false;
				}
				mappings.put(x,y);
			}
			for (int i=0; i<codomain.length; i++)
				if(!mappings.containsValue(codomain[i]))
					surj = false;
			if (notFun){
				System.out.println("not a function");
			}
			else if (inj && surj){
				System.out.println("bijective");
			}
			else if (surj){
				System.out.println("surjective");
			}
			else if (inj){
				System.out.println("injective");
			} else {
				System.out.println("neither injective nor surjective");
			}
		}
	}
}