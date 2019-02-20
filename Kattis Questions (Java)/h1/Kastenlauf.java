//V00874629 Knowles, Christian

/*

A point class is used to navigate the city and find if there is a beer store within 1000 meters.
If they cannot find a beer store, they run out of beer and are very sad :(
The PowerPoint class also checks to see if a point is visited so they do not go the wrong way.
The LinkedList path refers to the location they are currently at.  0 is added to the list which
represents J's house.  When they reach a location k, that location is added to the list and
the program checks the unchecked locations to see if a beer store is adjacent.

*/

import java.util.*;
import java.io.*;

class PowerPoint {

    int x;
    int y;
    boolean visit;

    public PowerPoint(){
    	this.x = 0;
    	this.y = 0;
    	this.visit = false;
    }
}

public class Kastenlauf{
	public static void main(String args[]) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int tests = Integer.parseInt(in.readLine());
		for(int i=0;i<tests;i++){
            int n = Integer.parseInt(in.readLine());
            n=n+2; //accounting for Jo's house and Bergkirchweih
            PowerPoint[] location = new PowerPoint[n];
            for(int j=0; j<n; j++){
            	location[j]=new PowerPoint(); //initializes all points
            }
            LinkedList<Integer> path = new LinkedList<Integer>();
            for(int j=0;j<n;j++){
                String[] tmp = in.readLine().split(" ");
                location[j].x = Integer.parseInt(tmp[0]);
                location[j].y = Integer.parseInt(tmp[1]);
            }
            path.add(0); //add starting point
            while(path.size()>0){
                int current = path.remove();
                if(!location[current].visit){
                    location[current].visit = true;
                    for(int k=0;k<n;k++){
                        if(current!=k){
                            if((Math.abs(location[current].x-location[k].x) + Math.abs(location[current].y-location[k].y))<=1000){
                            	//if the combined x and y coordinates are less than 1000, the next beer store is reached
                                path.add(k);
                            }
                        }
                    }
                }
            }
            if(location[n-1].visit){ //reaches end coordinate
            	System.out.println("happy");
            } else {
            	System.out.println("sad");
            }
        }
    }
}