import java.util.ArrayList;

public class Node implements Comparable<Node>{
	public int tag, distanceTo;
	public ArrayList<Integer> in, out;
	public boolean marked;
	
	
	public Node(int num){
		this.tag = num;
		this.in = new ArrayList<>();
		this.out = new ArrayList<>();
		this.marked = false;
	}
	
	public void mark(){
		this.marked = true;
	}
	
	@Override
	public int hashCode(){
		return this.tag;
	}
	@Override
	public String toString(){
		return String.format("(%d)", this.tag);
	}


	@Override
	public int compareTo(Node arg0) {
		// TODO Auto-generated method stub.
		return this.distanceTo-arg0.distanceTo;
	}
}