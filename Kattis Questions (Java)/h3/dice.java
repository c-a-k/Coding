//V00874629 Knowles, Christian

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class dice{
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		int tosses = in.nextInt();
		int sides = in.nextInt();
		int numbers = in.nextInt();
		double[][] probgrid = new double[tosses+10][sides+10];
		for(int i=0;i<tosses+10; i++){
		 	for(int j=0; j<sides+10; j++){
		 		probgrid[i][j]=0;
		 	}
		}
		probgrid[0][0]=1;
		for (int i=0; i<=tosses; i++){
    		for (int j=0; j<=sides; j++){
      			double p=j/(double)(sides);
      			double q=(sides-j)/(double)(sides);
      			probgrid[i+1][j]+=p*probgrid[i][j];
      			probgrid[i+1][j+1]+=q*probgrid[i][j];
    		}
  		}
  		double out=0.0;
  		for(int j=numbers; j<=sides; j++){
  			out+=probgrid[tosses][j];
  		}
		DecimalFormat formatter = new DecimalFormat("#.#########");     
		System.out.println(formatter.format(out));
	}
}