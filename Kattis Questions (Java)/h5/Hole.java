//V00874629 Knowles, Christian
//borrows heavily from QueenA
import java.util.*;

class Hole{
	//create variables so they can be used in both methods
	static int[] x;      // solution
	static boolean[] a;  // row free?
	static boolean[] b;  // / diag free?
	static boolean[] c; // \ diag free?
	static int count;
	static int N;

	static void gen(int col, boolean[][] board){
   		for(int row=0; row<N; row++){
   			if(!board[row][col]){ //check if hole in that spot
   				if (a[row] && b[row+col] && c[row-col+N]) { 
	        		x[col] = row;
	        		a[row] = b[row+col] = c[row-col+N] = false;
	        		if(col<N-1) gen(col+1,board); else count++;
	        		a[row] = b[row+col] = c[row-col+N] = true;
      			}
   			}
      		
   		}      
	}
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		for(;;){ //for each board description
			//insantiate new instances of variables
			x = new int[27];
			a = new boolean[27];
			b = new boolean[27];
			c = new boolean[27];
			count = 0;
			N = s.nextInt();
			int holes = s.nextInt();
			if (N == 0 && holes == 0){
				break;
			}
			//make a 2D array and file it with holes
			boolean[][] board = new boolean[N][N];
			for(int i=0; i<holes; i++){
				int x = s.nextInt();
				int y = s.nextInt();
				board[x][y] = true;
			}
			for (int i=0; i<2*N+2; i++) a[i] = b[i] = c[i] = true;
			
			gen(0,board);
			System.out.println(count);
		}
	}
}