//V00874629 Knowles, Christian
import java.io.*;
import java.util.*;

class Corridor{
	int x, y; //connection
	double shrink; // how much it will shrink Mikael
	Corridor(int point1, int point2, double ouch){
		x=point1;
		w=point2;
		shrink=ouch;
	}
	Corridor(){
		x=0;
		w=0;
		shrink=0.0;
	}
}

class shorty{
	public static void main(String args[]) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(in.ready()){
			Scanner s = new Scanner(in.readLine());
			int intersections = s.nextInt();
			int corridors = s.nextInt();
			if(intersections == 0 && corridors == 0)
				break;
			HashMap<String,Integer> hash = new HashMap<String,Integer>();
		}
	}
}