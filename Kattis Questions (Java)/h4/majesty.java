//V00874629 Knowles, Christian
import java.io.*;
import java.util.Scanner;

class majesty{
	public static void main(String args[]) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int queens = Integer.parseInt(in.readLine());
		boolean board[][] = new boolean[queens+1][queens+1];
		for(int j=0; j<queens; j++){
			Scanner s = new Scanner(in.readLine());
			int a = s.nextInt();
			int b = s.nextInt();
			board[a][b]=true;
			for(int i=0; i<queens; i++){
				if((board[a][i]&&i!=b) || (board[i][b]&&i!=a)){
					System.out.println("INCORRECT");
					System.exit(0);
				}
			}
			int temp1=a;
			int temp2=b;
			while(temp1<queens&&temp2<queens){ //going up right
				temp1++;
				temp2++;
				if(board[temp1][temp2]){
					System.out.println("INCORRECT");
					System.exit(0);
				}
			}
			temp1=a;
			temp2=b;
			while(temp1<queens&&temp2>0){ //going down right
				temp1++;
				temp2--;
				if(board[temp1][temp2]){
					System.out.println("INCORRECT");
					System.exit(0);
				}
			}
			temp1=a;
			temp2=b;
			while(temp1>0&&temp2<queens){ //going up left
				temp1--;
				temp2++;
				if(board[temp1][temp2]){
					System.out.println("INCORRECT");
					System.exit(0);
				}
			} 
			temp1=a;
			temp2=b;
			while(temp1>0&&temp2>0){ //going down left
				temp1--;
				temp2--;
				if(board[temp1][temp2]){
					System.out.println("INCORRECT");
					System.exit(0);
				}
			}
		}
		System.out.println("CORRECT");
	}
}