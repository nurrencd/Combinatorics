import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LinkedArcArray {

	public ArcNode[] arcs;
	public int arcCount, minWeight, maxWeight;
	
	private class ArcNode{
		ArcNode next, prev;
		Arc arc;
		
		private ArcNode(Arc arc){
			this.arc = arc;
			this.next = null;
			this.prev = null;
		}
	}
	
	
	public LinkedArcArray(int size){
		this.arcs = new ArcNode[size];
		this.arcCount = 0;
		this.minWeight = Integer.MAX_VALUE;
		this.maxWeight = Integer.MIN_VALUE;
	}

	public void addArc(Arc a){
		ArcNode an1 = new ArcNode(a);
		// adjust min / max weight values
		if (a.weight > this.maxWeight){
			this.maxWeight = a.weight;
		}
		if (a.weight < this.minWeight){
			this.minWeight = a.weight;
		}
		// if the indexed arc does not already exist
		if (this.arcs[a.src.tag]==null){
			this.arcs[a.src.tag]=an1;
		}
		else{
			//arc exists, keep appending ArcNodes
			ArcNode an2 = this.arcs[a.src.tag];
			while (an2.next!=null){
				an2 = an2.next;
			}
			an2.next = an1;
		}
		this.arcCount++;
	}
	
	public Arc getArc(int src, int dst){
		//TODO: add preliminary checks
		ArcNode n = this.arcs[src];
		if (n==null){
			throw new NoSuchElementException("getArc failed");
		}
		while (n.arc.dest.tag!=dst){
			n = n.next;
			if (n==null){
				throw new NoSuchElementException("getArc failed");
			}
		}
		return n.arc;
	}
	
	public Arc[] getOutArcs(int src, ArrayList<Integer> in){
		Arc[] arcs = new Arc[in.size()];
		int index = 0;
		for (int j : in){
			arcs[index]	= this.getArc(src, j);		
		}
		return arcs;
	}
	
	public ArrayList<Arc> getArcs(){
		ArrayList<Arc> arcList = new ArrayList<>();
		for (int i = 1; i < this.arcs.length;i++){
			if (this.arcs[i]!=null){
				ArcNode n = this.arcs[i];
				arcList.add(n.arc);
				while (n.next!=null){
					n=n.next;
					arcList.add(n.arc);
				}
			}
		}
		return arcList;
		
		
	}
	
	public int getMinWeight(){return this.minWeight;}
	public int getMaxWeight(){return this.maxWeight;}
	
	
	
}
