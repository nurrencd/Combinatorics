import java.util.ArrayList;

public class Graph {
	public Node[] nodes;
	public LinkedArcArray arcs;
	public int nodeCount, arcCount;
	public int[] distance, pred;
	public ArrayList<GraphResult> results;
	public ArrayList<Integer> sources;

	public Graph(Node[] nodes, int nodeCount, LinkedArcArray arcs, int arcCount) {
		this.nodes = nodes;
		this.arcs = arcs;
		this.nodeCount = nodeCount;
		this.arcCount = arcCount;
		this.distance = new int[nodeCount + 1];
		this.pred = new int[nodeCount + 1];
		this.results = new ArrayList<>();
		this.sources = new ArrayList<>();
	}

	public Arc getArc(int src, int dst) {
		return this.arcs.getArc(src, dst);
	}

	/**
	 * 
	 * Initializes the distance array to be ~infinity
	 *
	 * @param source
	 *            - the source node in the problem.
	 */
	public void initialize(int source) {
		
		for (int i = 1; i < this.nodes.length; i++) {
			if (i == source) {
				this.distance[i] = 0;
				this.nodes[i].distanceTo = 0;
			} else {
				this.distance[i] = Integer.MAX_VALUE;
				this.nodes[i].distanceTo = Integer.MAX_VALUE;
			}
			this.pred[i] = -1;
			this.nodes[i].marked = false;
		}
	}

	public void printResults() {
		for (Node n : this.nodes) {
			if (n != null) {
				System.out.println(String.format(n.toString() + " distance: %d   predecessor: %d", this.distance[n.tag],
						this.pred[n.tag]));
			}
		}
	}

	@Override
	public String toString() {
		if (this.nodes.length > 500) {
			return "Too big to output...";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 1; i < this.nodes.length; i++) {
			sb.append(this.nodes[i].toString());
			if (i % 8 == 0 || i == this.nodes.length - 1) {
				sb.append('\n');
			}
		}
		ArrayList<Arc> arcs = this.arcs.getArcs();
		for (int j = 0; j < arcs.size(); j++) {
			sb.append(arcs.get(j).toString());
			if (j % 3 == 2 || j == arcs.size() - 1) {
				sb.append('\n');
			} else {
				sb.append(", ");
			}
		}

		return sb.toString();
	}
}
