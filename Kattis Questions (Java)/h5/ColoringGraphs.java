import java.util.*;
import java.io.*;

public class ColoringGraphs {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {

    // Read in the number of nodes
    int n = Integer.valueOf(br.readLine());

    // Read in the graph
    boolean[][] adj = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      String[] line = br.readLine().split(" ");
      for (String str : line) {
        int j = Integer.valueOf(str);
        adj[i][j] = adj[j][i] = true;
      }
    }

    // Compute and output the minimum number of colors needed
    System.out.println((int) min(adj, 0, new int[n], 1));

  }

  // Use recursive backtracking to minimize the number of colors needed
  static double min(boolean[][] adj, int index, int[] colors, int nextColor) {

    int n = adj.length;

    // Base case
    if (index == n)
      return 0;

    double best = Double.POSITIVE_INFINITY;

    // Try all previously existing colors
    outer: for (int color = 1; color < nextColor; color++) {

      // Skip if this node is adjacent to another one with this color
      for (int i = 0; i < index; i++)
        if (adj[index][i] && colors[i] == color)
          continue outer;

        // Try this color
      colors[index] = color;
      best = Math.min(best, min(adj, index + 1, colors, nextColor));

    }

    // Try new color
    colors[index] = nextColor;
    best = Math.min(best, 1 + min(adj, index + 1, colors, nextColor + 1));
    System.out.println(best);
    return best;

  }

}

