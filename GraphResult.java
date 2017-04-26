
public class GraphResult {
	public long time, scans, updates;
	
	public GraphResult(long t, long s, long u){
		this.time = t;
		this.scans = s;
		this.updates = u;
	}
	
	public void getResults(){
		System.out.println(String.format("%d ms taken", this.time));
		System.out.println(String.format("%d arcs scanned", this.scans));
		System.out.println(String.format("%d distance labels updated", this.updates));
	}
}
