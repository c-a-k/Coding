//V00874629 Knowles, Christian

import java.util.Scanner;
import java.math.BigInteger;

public class treeinsert{
	static class Node{
		int value;
		int subnodes = 0;
		BigInteger permutation;
		Node[] children = new Node[2];
		Node(int num) {
			this.value = num;
		}
	}

	public static void main(String[] args) throws Exception{
		Scanner s = new Scanner(System.in);
		while(true){
			int num = s.nextInt();
			if(num==0){
				break;
			}
			Node root = new Node(0);
			for(int i=0; i<num; i++){
				addInt(s.nextInt(),root,0);
			}	
			findPerm(root);
			System.out.println(root.permutation);
			
		}
	}

	static void addInt(int n, Node parent, int child) {
		while (parent.children[child] != null) {
			parent = parent.children[child];
			child = (n < parent.value) ? 0 : 1;
		}
		parent.children[child] = new Node(n);
	}

	static void findPerm(Node n) {
		BigInteger[] permutations = new BigInteger[2];
		int left =0;
		int right =0;
		Node child = n.children[0];
		if (child == null) {
			permutations[0] = BigInteger.ONE;
			left = 0;
		} else {
			findPerm(child);
			permutations[0] = child.permutation;
			left = child.subnodes;
		}
		child = n.children[1];
		if (child == null) {
			permutations[1] = BigInteger.ONE;
			right = 0;
		} else {
			findPerm(child);
			permutations[1] = child.permutation;
			right = child.subnodes;
		}
		n.subnodes = left + right + 1;
		int j = n.subnodes-1;
		int k = left;
		if(k>j/2){
			k = j-k;
		}
		BigInteger combo = factorial(j).divide(factorial(k).multiply(factorial(j-k)));
		n.permutation = permutations[0].multiply(permutations[1]).multiply(combo);
	}

	static BigInteger factorial(int n) {
		BigInteger m = BigInteger.ONE;
		for (int i = 1; i<=n; i++) {
			m = m.multiply(BigInteger.valueOf(i));
		}
		return m;
	}
}
