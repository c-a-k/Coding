public class Queue<E>{

	private Node<E> front;
	private Node<E> back;

	public Queue(){
		this.front = null;
		this.back = null;
	}

	public boolean isEmpty(){
		return front==null;
	}

	public void enqueue(E element){
		Node<E> temp = new Node<E>(element, null);
		if(back!=null){
			back.next = temp;
		} else {
			front = temp;
		}
		back = temp;
	}

	public E peek(){
		if(front == null){
			throw new QueueEmptyException();
		}
		return front.item;
	}

	public E dequeue(){
		if(front == null){
			throw new QueueEmptyException();
		}
		E r = front.item;
		front = front.next;
		return r;
	}

	public static void main(String[] args){
		Queue<String> x = new Queue<String>();
		try{
			x.peek();
			System.out.println("FAIL");
		} catch(QueueEmptyException qe){
			System.out.println("EXCEPTION IS GOOD");
		}
		try{
			x.dequeue();
			System.out.println("FAIL");
		} catch(QueueEmptyException qe){
			System.out.println("EXCEPTION IS GOOD");
		}
		if(x.isEmpty()){
			System.out.println("EMPTY");
		} else {
			System.out.println("WRONG");
		}
		x.enqueue("Banana Bread");
		x.enqueue("Garlic Bread");
		if(x.peek().equals("Banana Bread")){
			System.out.println("Success");
		} else {
			System.out.println("Failure");
		}

		while(!x.isEmpty()){
			System.out.println(x.dequeue());
		}
		System.out.println("done");
	}

}