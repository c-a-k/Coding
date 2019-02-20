/*
 * StackRefBased.java
 *
 * Christian Knowles
 * March 12, 2017
 * 
 *
 * This class numerous methods that have been implemented from the generic Stack class
 * All of the functions are commonly used in a Stack ADT and each one has a unique purpose
 *
 */

public class StackRefBased<T> implements Stack<T> {

    private StackNode<T> top;
    private int count;

    public StackRefBased() { //default constructor with no top and no elements
        top = null;
        count = 0;
    }

    public int size() {
        return count; //size of stack
    }


    public boolean isEmpty() {
        return (count == 0); //determines if stack is empty or not
    }


    public void push(T data) {
        StackNode<T> n = new StackNode<T>(data);
        if(isEmpty()){ //corner case if stack is initially empty
            top = n;
            count++;
            return;
        }
        n.next = top; //next node points to previous top
        top = n; //top points to new node
        count++;
    }


    public T pop() throws StackEmptyException {
        if(isEmpty()){ //check for exception
            throw new StackEmptyException();
        }
        StackNode<T> temp = top; //saved for return
        top = top.next; //previous top has been removed from the list
        count--;
        return temp.data;
    }


    public T peek() throws StackEmptyException {
        if(isEmpty()){ //check for exception
            throw new StackEmptyException();
        }
        return top.data; //return data of top without changing stack
    }


    public void makeEmpty() {
        top = null; //top now points to null, thus eleminating the list
        count = 0; //count is also 0 since there are no more elements
    }


    public String toString() { //this method does not do anything except allow the class to compile
        StackNode<T> curr = top;
        String stackList = "";
        for(int i=0; i<count; i++){
            curr = curr.next;
        }
        return stackList;
    }
}

