//V00874629 Knowles, Christian

import java.io.*;
import java.util.*;
@SuppressWarnings("unchecked")
public class PickUpSticks{

    public static ArrayList<Integer>[] pile;
    public static boolean[] visited;
    public static int[] edgeCount;
    public static Stack stack;

	public static void main(String[] args) throws IOException{
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st=new StringTokenizer(in.readLine());
        int sticks=Integer.parseInt(st.nextToken());
        int lines=Integer.parseInt(st.nextToken());

        pile=new ArrayList[sticks+1];
        edgeCount=new int[sticks+1];

		for(int i=0; i<lines; i++){
			st=new StringTokenizer(in.readLine());
            int stick=Integer.parseInt(st.nextToken());
            int under=Integer.parseInt(st.nextToken());

                if(pile[stick]==null){
                    pile[stick]=new ArrayList<>();
                }
                edgeCount[under]++;
                pile[stick].add(under);
		}
		  
            StringBuilder sb = new StringBuilder();
            boolean hasStartingPoint=false;
            for (int i=1;i<=sticks && !hasStartingPoint;i++) {
                hasStartingPoint=(edgeCount[i]==0);
            }

            if(hasStartingPoint){
                stack = new Stack();
                visited=new boolean[sticks+1];
                for (int i=1;i<=sticks;i++) {
                    if (edgeCount[i]==0) {
                        sortHelp(i);
                    }
                }
                
                while (stack.empty()==false){
                    sb.append(stack.pop() + "\n");
                }
            } else {
                System.out.println("IMPOSSIBLE");
                System.exit(0);
            }
        while (stack.empty()==false){
            sb.append(stack.pop() + "\n");
        }
   		System.out.print(sb.toString());
	}


    public static void sortHelp(int stick){
        if(!visited[stick]) {
            visited[stick]=true;
            if (pile[stick]!=null) {
                for(int i=0;i<pile[stick].size();i++) {
                    sortHelp(pile[stick].get(i));
                }
            }
            stack.push(stick);
        }
    }


}