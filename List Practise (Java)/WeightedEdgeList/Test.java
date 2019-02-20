/*
   The Test class is provided to you for CSC 225
   by Wendy Myrvold.  You do not have permission to distribute
   this code or to use it for any other purpose.

   Make sure that your program runs with this version
   of test (with the checkList code included).

*/


import java.util.*;
import java.io.*;

public class Test
{
/*
   This checkList method returns true if the list
   is valid and otherwise it prints an error message
   and returns false.

   Warning: students who did not write code to check
   their lists often had problems with them.
   You are strongly advised to check your lists
   since if your lists are not correct your code
   is not correct.
*/

   static boolean checkList(WeightedEdgeList x){

   if(x==null){
        return (false);
       }
       if(x.m<0){
          System.out.println("Error: x is " + x.m);
          System.exit(0);
       }
       if(x.m==0){
          if(x.start != null){
            System.out.println("Error: list of size 0 has start node");
            System.exit(0);
          }
          if(x.rear != null){
            System.out.println("Error: list of size 0 has rear node");
            System.exit(0);
          }
       }
       if(x.start == null){
            System.out.println("Error: nonzero list contains null start");
            System.exit(0);
       }
       if(x.rear == null){
            System.out.println("Error: nonzero list contains null rear");
            System.exit(0);
       }
       if(x.rear.next != null){
            System.out.println("Error: list is not null terminated");
            System.exit(0);
       }

       WeightedEdgeNode prev, curr;
       curr = x.start;
       for(int i=0; i<x.m; i++){
            if(curr == null){
                System.out.println("Error: list ends early before item "
                    + (i+1) + " of " + x.m);
                System.exit(0);
            }
            if(curr.edge[0]<1){
                System.out.println("Error: list had an invalid weight" + curr.edge[0]);
                System.exit(0);
            }
            if(curr.edge[1]<0 || curr.edge[1]>x.n){
                System.out.println("Error: list had an invalid lesser node (u)" + curr.edge[1]);
                System.exit(0);
            }
            if(curr.edge[2]<0 || curr.edge[2]>x.n){
                System.out.println("Error: list had an invalid greater node (v)" + curr.edge[2]);
                System.exit(0);
            }
            prev = curr;
            curr = curr.next;
       }

      return(true);
   }

   public static void main(String args[])
   {
       WeightedEdgeList G, tree;
       int graph_num;

//     Use for checking the final spanning tree.

       Scanner in = new Scanner(System.in);

       graph_num=0;

       G= WeightedEdgeList.readWeightedEdgeList(in);

       while (G!= null)
       {
           graph_num++;
           System.out.println("Graph: " + graph_num);
           G.printWeightedEdgeList();

           checkList(G);

           G.edgeSort();
           checkList(G);

           System.out.println("After sorting the edges by weight:");
           G.printWeightedEdgeList();

           tree= G.minWeightTree();

//         You can return null if you have no code for this.

           if (tree != null) 
           {
               System.out.println("The minimum weight tree:");
               tree.printWeightedEdgeList();
    
               System.out.println("The chords for this tree:");
               G.printWeightedEdgeList();

               checkList(G);
               checkList(tree);
           }

           G= WeightedEdgeList.readWeightedEdgeList(in);
       }
    }
}
