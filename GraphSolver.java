import java.util.LinkedList;
import java.util.Random;

public class GraphSolver {

	public static void shuffleArray(Arc[] arcs) {
		// System.out.println("Before: " + Arrays.toString(arcs));
		int offset = 0;
		Random r = new Random();
		Arc temp;
		for (int i = 0; i < arcs.length; i++) {
			int index = offset + r.nextInt(arcs.length - offset);
			temp = arcs[i];
			arcs[i] = arcs[index];
			arcs[index] = temp;
			offset++;
		}
		// System.out.println("After: " + Arrays.toString(arcs));

	}
	
	private static void negativeCycleSearch(Graph g, int start){
		
	}

	public static void preliminaryDistanceSearch(Graph g, int source) {
		// breadth first
		g.initialize(source);
		LinkedList<Node> list = new LinkedList<>();
		Node n = g.nodes[source];
		list.add(n);
		n.mark();

		Node j;
		while (list.size() != 0) {
			n = list.removeFirst();
			for (int i : n.out) {
				j = g.nodes[i];
				if (!j.marked) {
					list.offer(j);
					j.mark();
					g.distance[j.tag] = g.distance[n.tag] + g.getArc(n.tag, j.tag).weight;
				}
			}
		}

	}

	public static GraphResult GenericAlgorithm(Graph g, int source) {
		// initializes G and gets preliminary d[i].
		preliminaryDistanceSearch(g, source);
//		g.initialize(source);
		Arc[] arcs = g.arcs.getArcs().toArray(new Arc[g.arcCount - 1]);
		shuffleArray(arcs);
		shuffleArray(arcs);

		// O(n^2*m*C), so check cost for max iterations
		long maxIterations = 2*(long)g.nodeCount * (g.arcs.maxWeight - g.arcs.minWeight);
		long iterCount = 0;
		long arcScanCount = 0;
		long dUpdatedCount = 0;
		/*
		 * used for determining a starting point in a negative cycle search
		 */
		int lastNodeTouched = 0;
		boolean cont = true; // will be changed if no arc change exists
		long startTime = System.currentTimeMillis();
		while (cont) {
			cont = false; // triggers end of loop if not corrected in the check
			for (Arc a : arcs) {
				arcScanCount++;
				int i = a.src.tag;
				int j = a.dest.tag;
				// value of d[i] + weight
				int value = Integer.MAX_VALUE;
				// only update value if meaningful, avoid overflow
				if (g.distance[i] != Integer.MAX_VALUE) {
					value = g.distance[i] + a.weight;
				}

				if (g.distance[j] > value) {
					lastNodeTouched = j;
					dUpdatedCount++;
					g.distance[j] = value;
					g.pred[j] = i;
					cont = true;
				}
			}
			iterCount++;
			if (iterCount > maxIterations) {
				System.out.println("NEGATIVE CYCLE DETECTED, QUITTING LOOP");
				break;
			}
		}
		long endTime = System.currentTimeMillis();
		GraphResult result = new GraphResult(endTime - startTime, arcScanCount, dUpdatedCount);
		return result;

	}

	// change negative detection to d[i] < -nC, or iterCount > N
	public static GraphResult ModifiedNodeListAlgorithm(Graph g, int source, int type) {
		g.initialize(source);
		// remove and offer
		HashedLinkedList list = new HashedLinkedList(g.nodeCount, type);
		list.offer(g.nodes[source]);
		//doesnt apply for stack implementations
		//naive upperbound on iterations...
		long maxIterations = 2*((long)g.nodeCount) * (g.arcs.maxWeight - g.arcs.minWeight);
		if (type==1){
			//stack is exponential... not sure of bounds
			maxIterations=Long.MAX_VALUE;
		}
		// could not get this to work right...
//		else if(type==0){
//			maxIterations = (long) g.nodeCount+1;
//		}

		long iterCount = 0;
		long arcScanCount = 0;
		long dUpdateCount = 0;
		
		int lastNodeTouched = 0;

		Node i, j;
		Arc a;

		long start = System.currentTimeMillis();
		while (!list.isEmpty()) {
			i = list.remove();
			for (int k : i.out) {
				j = g.nodes[k];
				a = g.getArc(i.tag, j.tag);
				int value = g.distance[i.tag] + a.weight;
				if (g.distance[j.tag] > value) {
					lastNodeTouched = j.tag;
					
					// update pred, value
					j.distanceTo = value;
					g.distance[j.tag] = value;
					g.pred[j.tag] = i.tag;
					// add if not in list
					
					if (!list.hasNode(j.tag)) { // is Hashed, O(1)
						list.offer(j);
					}
					dUpdateCount++;
					int limit = -1*g.nodeCount*Integer.max(Math.abs(g.arcs.maxWeight), 
							   Math.abs(g.arcs.minWeight));
					if (g.distance[j.tag] < limit) {
						System.out.println("negative cycle detected");
					}
				}
				arcScanCount++;
			}
			iterCount++;
			if (iterCount > maxIterations) {
				System.out.println(String.format("Negative cycle detected. Node  %d is connected.",lastNodeTouched));
				break;
			}
		}
		
		long end = System.currentTimeMillis();
		
		GraphResult res = new GraphResult(end - start, arcScanCount, dUpdateCount);
		return res;
	}



}
