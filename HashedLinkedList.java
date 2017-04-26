import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class HashedLinkedList {
	// determines functional operation implementation
	// by passing values into constructor
	// no user interaction
	public static int FIFO = 0;
	public static int STACK = 1;
	public static int DEQUEUE = 2;
	public static int PRIORITY = 3;

	public LinkedList<Node> list;
	public Stack<Node> stack;
	public PriorityQueue<Node> priorityQueue;
	public int size, capacity, type;
	public int[] hash;
	public int[] dequeueHash;

	public HashedLinkedList(int capacity, int type) {
		this.type = type;
		this.capacity = capacity;
		this.size = 0;

		this.hash = new int[capacity + 1];
		this.list = new LinkedList<>();
		this.stack = new Stack<>();
		if (this.type == DEQUEUE) {
			// dont want to waste memory if not needed
			this.dequeueHash = new int[capacity + 1];
		} else if (this.type == PRIORITY) {
			this.priorityQueue = new PriorityQueue<>();
		}
	}

	public Node remove() {
		Node n = null;
		if (this.type == FIFO || this.type == DEQUEUE) {
			n = this.list.remove();
		} else if (this.type == STACK) {
			n = this.stack.pop();
		} else if (this.type == PRIORITY) {
			n = this.priorityQueue.poll();
		}
		this.hash[n.tag] = 0;
		this.size--;
		return n;
	}

	public boolean hasNode(int i) {
		return this.hash[i] == 1;
	}

	public void offer(Node n) {
		if (this.type == FIFO) {
			this.list.offer(n);
		} else if (this.type == STACK) {
			this.stack.push(n);
		} else if (this.type == DEQUEUE) {
			if (this.dequeueHash[n.tag] == 1) {
				this.list.offerFirst(n);
			} else {
				this.list.offerLast(n);
				this.dequeueHash[n.tag] = 1; // mark as hvaing been in array
			}
		} else if (this.type == PRIORITY) {
			this.priorityQueue.add(n);
		}
		this.hash[n.tag] = 1;
		this.size++;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}
}
