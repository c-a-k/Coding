import java.util.*;

public class BSTMap<K extends Comparable<K>, V > implements  Map<K, V>  {

	private BinarySearchTree m;

	public BSTMap () {
		m = new BinarySearchTree();
	}

	public boolean containsKey(K key) {
		try{
			m.find(key);
			return true;
		} catch(KeyNotFoundException e) {
			return false;
		}
	}

	public V get (K key) throws KeyNotFoundException {
		return (V) m.find(key);
	}

	public List<Entry<K,V> >	entryList() {
		return m.entryList();
	}

	public void put (K key, V value) {
		m.insert(key,value);
	}

	public int size() {
		return m.size();
	}

	public void clear() {
		m.clear();
	}

	public int getGetLoopCount() {
		return m.getFindLoopCount();
	}

	public int getPutLoopCount() {
		return m.getInsertLoopCount();
	}

	public void resetGetLoops() {
		m.resetFindLoops();
	}
	public void resetPutLoops() {
		m.resetInsertLoops();
	}
}
