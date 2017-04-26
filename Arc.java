
public class Arc implements Comparable<Arc>{
	public Node src;
	public Node dest;
	public int weight;
	
	public Arc(Node src, Node dest, int weight){
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		
	}
	@Override
	public int hashCode(){
		//this should be sufficiently unique
		return this.src.tag + this.dest.tag * this.weight;
	}
	@Override
	public int compareTo(Arc arg0) {
		// TODO Auto-generated method stub.
		return this.weight-arg0.weight;
	}
	
	@Override
	public String toString(){
		return String.format("[(%d, %d) %d]", this.src.tag, this.dest.tag, this.weight);
	}
	
}
