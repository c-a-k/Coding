//V00874629 Knowles, Christian

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class colours{
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int verts = Integer.parseInt(in.readLine());
		boolean [][] adj = new boolean[verts][verts];
		for(int i=0; i<verts; i++){ //building graph from adjacencies
			String[] line = in.readLine().split(" ");
			for(int k=0; k<line.length; k++){
				int j = Integer.parseInt(line[k]);
				adj[i][j] = true;
				adj[j][i] = true;
			}
		}
		int[] colours = new int[verts];
		int answer = minColours(adj, colours, 0, 1);
		System.out.println(answer);
	}
	//recursive backtracking on graph to minimize # of colours
	static int minColours(boolean[][] adj, int[] colours, int index, int next){
		if(index == adj.length)//base case
			return 0;
		int minimum = 11; //max number of colors possible, reduce from here
		for(int i=1; i<next; i++){ //checks all previous colours
			int skip = 0;
			for(int j=0; j<index; j++)
				if(colours[j] == i && adj[index][j])
					skip = 1; //skip if adjacent to node with this colour
			if(skip ==1) continue;
			//test this colour since it's not adjacent to the same colour
			colours[index] = i;
			minimum = Math.min(minimum, minColours(adj, colours, index+1, next));
		}
		//test a new colour
		colours[index] = next;
		return Math.min(minimum, 1+minColours(adj, colours, index+1, next+1));
	}
}