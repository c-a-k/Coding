import java.util.*;

//
// An implementation of a binary search tree.
//
// This tree stores both keys and values associated with those keys.
//
// More information about binary search trees can be found here:
//
// http://en.wikipedia.org/wiki/Binary_search_tree
//
// Note: Wikipedia is using a different definition of
//       depth and height than we are using.  Be sure
//       to read the comments in this file for the
//	 	 height function.
//
class BinarySearchTree <K extends Comparable<K>, V>  {

	public static final int BST_PREORDER  = 1;
	public static final int BST_POSTORDER = 2;
	public static final int BST_INORDER   = 3;

	// These are package friendly for the TreeView class
	BSTNode<K,V>	root;
	int		count;

	int		findLoops;
	int		insertLoops;

	public BinarySearchTree () {
		root = null;
		count = 0;
		resetFindLoops();
		resetInsertLoops();
	}

	public int getFindLoopCount() {
		return findLoops;
	}

	public int getInsertLoopCount() {
		return insertLoops;
	}

	public void resetFindLoops() {
		findLoops = 0;
	}
	public void resetInsertLoops() {
		insertLoops = 0;
	}

	//
	// Purpose:
	//
	// Insert a new Key:Value Entry into the tree.  If the Key
	// already exists in the tree, update the value stored at
	// that node with the new value.
	//
	// Pre-Conditions:
	// 	the tree is a valid binary search tree
	//
	public void insert (K k, V v) {
		BSTNode<K,V> n  = new BSTNode<K,V>(k,v);
		if(root == null){
			root = n;
			count++;
			return;
		}
		BSTNode<K,V> cur = root;
		while(true){
			insertLoops++;
			if(k.compareTo(cur.key)<0 && cur.left!=null){
				cur=cur.left;
			}
			else if(k.compareTo(cur.key)>0 && cur.right!=null){
				cur=cur.right;
			}
			else if(k.compareTo(cur.key)<0 && cur.left==null){
				cur.left = n;
				count++;
				return;
			}
			else if(k.compareTo(cur.key)>0 && cur.right==null){
				cur.right = n;
				count++;
				return;
			} else {
				cur.value = v;
				return;
			}
		}
	}

	//
	// Purpose:
	//
	// Return the value stored at key.  Throw a KeyNotFoundException
	// if the key isn't in the tree.
	//
	// Pre-conditions:
	//	the tree is a valid binary search tree
	//
	// Returns:
	//	the value stored at key
	//
	// Throws:
	//	KeyNotFoundException if key isn't in the tree
	//
	public V find (K key) throws KeyNotFoundException {
		BSTNode<K,V> cur = root;
		int test = cur.key.compareTo(key);
		if(count ==0){
			throw new KeyNotFoundException();
		}
		if(test==0){
			return cur.value;
		}
		while(test != 0){
			findLoops++;
			if(test>0){
				if(cur.left==null){
					throw new KeyNotFoundException();
				} else {
					test = cur.left.key.compareTo(key);
					cur = cur.left;
				}
			}
			if(test<0){
				if(cur.right==null){
					throw new KeyNotFoundException();
				} else {
					test = cur.right.key.compareTo(key);
					cur = cur.right;
				}
			} 
		}
		return cur.value;
	}

	//
	// Purpose:
	//
	// Return the number of nodes in the tree.
	//
	// Returns:
	//	the number of nodes in the tree.
	public int size() {
		return count;
	}

	//
	// Purpose:
	//	Remove all nodes from the tree.
	//
	public void clear() {
		count = 0;
		root = null;
	}

	//
	// Purpose:
	//
	// Return the height of the tree.  We define height
	// as being the number of nodes on the path from the root
	// to the deepest node.
	//
	// This means that a tree with one node has height 1.
	//
	// Examples:
	//	See the assignment PDF and the test program for
	//	examples of height.
	//
	public int height() {
		return height2(root);
	}

	public int height2(BSTNode<K,V>	root){
		if(root == null){
			return 0;
		}
			return 1+ Math.max(height2(root.left), height2(root.right));
	}

	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a level-order
	// traversal of the tree.
	//
	// Level order is most commonly implemented using a queue of nodes.
	//
	//  From wikipedia (they call it breadth-first), the algorithm for level order is:
	//
	//	levelorder()
	//		q = empty queue
	//		q.enqueue(root)
	//		while not q.empty do
	//			node := q.dequeue()
	//			visit(node)
	//			if node.left != null then
	//			      q.enqueue(node.left)
	//			if node.right != null then
	//			      q.enqueue(node.right)
	//
	// Note that we will use the Java LinkedList as a Queue by using
	// only the removeFirst() and addLast() methods.
	//
	public List<Entry<K,V>> entryList() {
		List<Entry<K,V>> l = new LinkedList<Entry<K,V>>();
		LinkedList<BSTNode> q = new LinkedList<BSTNode>();
		q.addLast(root);
		while(!q.isEmpty()){
			BSTNode<K,V> node = q.removeFirst();
			Entry<K,V> n = new Entry<K,V>(node.key, node.value);
			l.add(n);
			if(node.left!=null){
				q.addLast(node.left);
			}
			if(node.right!=null){
				q.addLast(node.right);
			}
		}
		return l;
	}

	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a traversal 
	// specified by the parameter which.
	//
	// If which is:
	//	BST_PREORDER	perform a pre-order traversal
	//	BST_POSTORDER	perform a post-order traversal
	//	BST_INORDER	perform an in-order traversal
	//  you could maybe use a stack for this
	public List<Entry<K,V>> entryList (int which) {
		List<Entry<K,V>> l = new LinkedList<Entry<K,V>>();
		if(which == BST_PREORDER){
			doPreOrder(root, l);
		}
		if(which == BST_POSTORDER){
			doPostOrder(root, l);
		}
		if(which == BST_INORDER){
			doInOrder(root, l);
		}
		return l;
	}

	private void doInOrder (BSTNode<K,V> n, List<Entry<K,V>> l){
		if(n!=null){
			doInOrder(n.left, l);
			Entry<K,V> node = new Entry<K,V>(n.key, n.value);
			l.add(node);
			doInOrder(n.right, l);
		}
	}

	private void doPreOrder (BSTNode<K,V> n, List<Entry<K,V>> l){
		if(n!=null){
			Entry<K,V> node = new Entry<K,V>(n.key, n.value);
			l.add(node);
			doPreOrder(n.left, l);
			doPreOrder(n.right, l);
		}
	}

	private void doPostOrder (BSTNode<K,V> n, List<Entry<K,V>> l){
		if(n!=null){
			doPostOrder(n.left, l);
			doPostOrder(n.right, l);
			Entry<K,V> node = new Entry<K,V>(n.key, n.value);
			l.add(node);
		}
	}

	// Your instructor had the following private methods in his solution:
	// private void doInOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPreOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPostOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private int doHeight (BSTNode<K,V> t)
}
