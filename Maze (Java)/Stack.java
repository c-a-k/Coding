
public interface Stack<T>
{
	int size();

	boolean isEmpty();

	void push (T element);

	T pop() throws StackEmptyException;

	T peek() throws StackEmptyException;

	void makeEmpty();

	String toString();
}
