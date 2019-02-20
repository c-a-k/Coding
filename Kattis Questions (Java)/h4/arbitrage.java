//V00874629 Knowles, Christian
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class arbitrage{
	public static void main(String args[]) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(in.ready()){
			int currencies = Integer.parseInt(in.readLine());
			if(currencies==0) //end of input
				break;
			double distance[][] = new double[currencies][currencies];
			List<String> rates = new ArrayList<>(Arrays.asList(in.readLine().split(" ")));
			int conversions = Integer.parseInt(in.readLine());
				for(int i=0; i<conversions; i++){
					Scanner s = new Scanner(in.readLine());
					int firstRate = rates.indexOf(s.next());
					int secondRate = rates.indexOf(s.next());
					String[] exchange = s.nextLine().trim().split(":");
					double exchangeRate = Double.parseDouble(exchange[0]) / Double.parseDouble(exchange[1]);
					distance[firstRate][secondRate]=exchangeRate;
				}
			
			for(int i=0;i<currencies;i++)
				for(int j=0;j<currencies;j++)
					for(int k=0;k<currencies;k++){
						if (distance[j][i] < (1E-57) || distance[i][k] < (1E-57)) continue; //can't use 0 in case of doubles being off
						if (distance[j][k] < (1E-57) || (distance[j][i]*distance[i][k] < distance[j][k])) {
							distance[j][k] = distance[j][i] * distance[i][k];
						}
					}
			int set = 0;
			for (int i=0; i<currencies; i++) {
				if (distance[i][i] != 0 && distance[i][i]<1){
					System.out.println("Arbitrage");
					set = 1;
					break;
				}
			}
			if(set==0)
				System.out.println("Ok");
		}
	}
}