// You can use this to start testing your program.

import java.util.*;
import java.io.*;

// This program tests the routines students write for assignment
// 2 Part B for CSC 225 in Fall 2012.

public class Test
{
   public static boolean checkList(LinkedList x)
   {

//     Fill in your code for this.

       return(true);
   }
   public static void main(String args[])
   {
       int nCompareTest;
       LinkedList x, y;
       int cmp;

       int nSortTest;
       int problem_number;
       BigIntegerList problem;
 
       Scanner in = new Scanner(System.in);

//     Test the compare method.

       nCompareTest= LinkedList.readInteger(in);

       System.out.println("Testing the compare method: " + nCompareTest + " tests.");

       for (problem_number=1; problem_number <= nCompareTest; problem_number++)
       {
           x= LinkedList.readBigInteger(in);
           y= LinkedList.readBigInteger(in);

           System.out.print("Problem " );
           if (problem_number < 10) System.out.print(" ");
           System.out.print(problem_number + ": ");
 
           cmp= x.compare(y);
           x.printBigInteger(8);

           if (cmp < 0)       System.out.print(" < ");
           else if (cmp >  0) System.out.print(" > ");
           else               System.out.print(" = ");

           y.printBigInteger(8);
 
           System.out.println();
       }

       nSortTest= LinkedList.readInteger(in);
       System.out.println("-----------------------------------------------------");
       System.out.println("Testing the quickSort method: " + nSortTest + " tests.");

       for (problem_number=1; problem_number <= nSortTest; problem_number++)
       {
           System.out.println("-----------------------------------------------------");
           System.out.println("Sort Problem " + problem_number);
           problem= BigIntegerList.readBigIntegerList(in);
           System.out.println("Before Sorting:");
           problem.printBigIntegerList();
           problem.quickSort(0);
           System.out.println("After Sorting:");
           problem.printBigIntegerList();
       }
   }
}
