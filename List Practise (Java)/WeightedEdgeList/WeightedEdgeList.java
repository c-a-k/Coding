/*

Christian Knowles
V00874629
November 24, 2017
WeightedEdgeList.java
CSC 225 201709 A01 Assignment #5

*/

import java.util.*;
import java.io.*;
public class WeightedEdgeList{
    public static boolean debug= true; // Change to false before submitting.

    int n;
    int m;
    WeightedEdgeNode start;
    WeightedEdgeNode rear;

/*  edgeSort utilizes a variation on radix sort to put the edges of the list in ascending order
    Edge[2] is sorted first with m buckets, then grouped back together
    Edge[1] is sorted second with m buckets, then grouped back together
    The weight (Edge[0]) is sorted third by first finding the maximum weight (max) and making max buckets, then grouping them together
    When the list is printed it appears to be in lexographic order
*/

    public void edgeSort(){
        
        WeightedEdgeList[] bucketV = new WeightedEdgeList[m];
        for(int i=0; i<m; i++){
            bucketV[i] = new WeightedEdgeList();
            //initializes each element in the array
        }
        WeightedEdgeNode current = start;
        int value;
        WeightedEdgeNode node;
        
        //list is sorted according to value of v
        while(current!=null){
            node = current;
            value = node.edge[2];
            current = current.next;
            bucketV[value].addRear(node);
        }
        //starter variable determines the first non-empty bucket to concatonate
        int starterV = 0;
        while(bucketV[starterV].m==0){
            starterV++;
        }
        start = bucketV[starterV].start;
        rear = bucketV[starterV].rear;
        //non-empty buckets are joined together
        for(int i=starterV; i<m-1; i++){
            if(bucketV[i+1].m>0){
                rear.next = bucketV[i+1].start;
                rear = bucketV[i+1].rear;
            }
        }

        WeightedEdgeList[] bucketU = new WeightedEdgeList[m];
        for(int i=0; i<m; i++){
            bucketU[i] = new WeightedEdgeList();
        }
        current = start;
        value =0;
        //list is sorted according to value of u
        while(current!=null){
            node = current;
            value = node.edge[1];
            current = current.next;
            bucketU[value].addRear(node);
        }
        int starterU = 0;
        while(bucketU[starterU].m==0){
            starterU++;
        }
        start = bucketU[starterU].start;
        rear = bucketU[starterU].rear;
        for(int i=starterU; i<m-1; i++){
            if(bucketU[i+1].m>0){
                rear.next = bucketU[i+1].start;
                rear = bucketU[i+1].rear;
            }
        }

        int max=0;
        current = start;
        while(current!=null){
            if(current.edge[0]>max){
                max = current.edge[0];
            }
            current = current.next;
        }
        WeightedEdgeList[] bucketW = new WeightedEdgeList[max];
        for(int i=0; i<max; i++){
            bucketW[i] = new WeightedEdgeList();
        }
        current = start;
        value =0;
        //list is sorted according to value of w
        while(current!=null){
            node = current;
            value = node.edge[0];
            current = current.next;
            bucketW[value-1].addRear(node);
        }
        int starterW = 0;
        while(bucketW[starterW].m==0){
            starterW++;
        }
        start = bucketW[starterW].start;
        rear = bucketW[starterW].rear;
        for(int i=starterW; i<max-1; i++){
            if(bucketW[i+1].m>0){
                rear.next = bucketW[i+1].start;
                rear = bucketW[i+1].rear;
            }
        }
    }

/*  minWeightTree removes all the necessary edge nodes from the original list
    in order to form a minimum weight spanning tree that is a new list
    This method utilizes Kruskal's algorithm to find the spanning tree
    This method also utilizes the Disjoint-set data structure (UnionFind)
    to keep track of if the spanning tree forms a cycle or not
*/
    public WeightedEdgeList minWeightTree(){

        WeightedEdgeList tree = new WeightedEdgeList();
        WeightedEdgeNode prev = start;
        WeightedEdgeNode current = start;
        WeightedEdgeNode node;
        UnionFind check = new UnionFind(n);
        tree.n = n;
        while(current!=null){
            node = current;
            //if both nodes are disjointed (aka not unioned)
            if(!check.same_component(node.edge[1],node.edge[2])){
                m--;
                //node values are unioned for future reference
                check.union(node.edge[1],node.edge[2]);
                //the desired node from the original list is removed
                if(current==start){
                    current=current.next;
                    prev = current;
                    start=start.next;
                } else {
                    prev.next = current.next;
                    current = current.next;
                }
                //and then the node is added to the spanning tree
                tree.addRear(node);
            } else {
                prev = current;
                current = current.next;
            }
        }
        
        return(tree);
            
    }

// Do not make any changes to code below this line:
//----------------------------------------------------------//

    public WeightedEdgeList(int num_vertex, int num_edge, 
              WeightedEdgeNode start_node, WeightedEdgeNode rear_node)
    {
        n=num_vertex;
        m=num_edge;
        start= start_node;
        rear= rear_node;
    }

    public WeightedEdgeList()
    {
        n=0;
        m=0;
        start= null;
        rear= null;
    }

/*  
    The input format consists of two integers
    n m 
    where 
    n is the number of nodes of the graph, and
    m is the number of edges of the graph.
    Legal edge weights are always greater than or equal to 1.
    Then for each edge (u, v) with weight w the input contains:
    w  u  v
*/

    public static WeightedEdgeList readWeightedEdgeList(Scanner in)
    {
        WeightedEdgeList G;
        WeightedEdgeNode add_node;

        int num_edge;
        int w, u, v;
        int i;

        if (! in.hasNextInt()) return null;

        G= new WeightedEdgeList();
        G.n= in.nextInt();

        if (! in.hasNextInt())
        {
           System.out.println("Missing value for m.");
           return(null);
        }

        num_edge= in.nextInt();

        if (debug) System.out.println("n= " + G.n + "  m= " + num_edge);

        for (i=0; i < num_edge; i++)
        {
            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing weight for edge " + (i+1));
               return(null);
            }
            w= in.nextInt();
            if (w < 1)
            {
               System.out.println("Error- Invalid edge weight " + w);
               return(null);
            }

            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing u for edge " + (i+1));
               return(null);
            }
            u= in.nextInt();

            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing v for edge " + (i+1));
               return(null);
            }
            v= in.nextInt();

            if (u < 0 || v < 0 || u >= G.n || v >= G.n)
            {
                System.out.println("Error- Invalid edge: " + u + " " + v);
                return(null);
            } 

            add_node= new WeightedEdgeNode(w, u, v, null);
            G.addRear(add_node);
        }
        return(G);
    }
    public void addRear(WeightedEdgeNode add_node)
    {
        if (start == null)
        {
            start= add_node;
        }
        else
        {
            rear.next= add_node;
        }
        rear= add_node;
        rear.next= null;
        m++;
    }

    public void printWeightedEdgeList()
    {
        WeightedEdgeNode current;

        int n_per_line=5;
        int num;
        int i;

        current= start;
        num=0;
        while (current != null)
        {
            System.out.format("[%3d (%3d, %3d)] ", current.edge[0], 
                                      current.edge[1], current.edge[2]);
            num++;
            if (num== n_per_line)
            {
               System.out.println();
               num=0;
            }
            current= current.next;
        }
        if (num!= 0) System.out.println();
    }
}
