import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphGenerator {

	public static Graph generateGraph(File f) throws FileNotFoundException, NoSuchFieldException {
		Scanner s = new Scanner(f);

		int nodeCount = 0, arcCount = 0; // total node count defined by "p" line
		int arcTagCounter = 0; // indexes and uniquely tags arcs as they are
								// made

		/*
		 * temp integers used for node 1, node 2, and weight used in arc
		 * creation line
		 */
		int n1, n2, w1;

		String line;
		String[] components; // elements in each line

		Node[] nodes = new Node[5]; // this will get rewritten, just a temp size
		LinkedArcArray arcs = new LinkedArcArray(5);

		// scan file to generate graph
		while (s.hasNextLine()) {
			line = s.nextLine();
			components = line.split(" ");
			switch (components[0]) { // content indicator c, p, or a for .gr
			case "c":
				// comment line
				break;
			case "p":
				// Problem line "p sp n m"
				nodeCount = Integer.decode(components[2]);
				arcCount = Integer.decode(components[3]);
				nodes = new Node[nodeCount + 1]; // nodes start at value 1
				arcs = new LinkedArcArray(nodeCount + 1);
				break;
			case "a":
				// arc and node creation
				n1 = Integer.decode(components[1]);
				n2 = Integer.decode(components[2]);
				w1 = Integer.decode(components[3]);
				// each new line may repeat nodes
				if (nodes[n1] == null) {
					nodes[n1]= new Node(n1);
				}
				if (nodes[n2] == null) {
					nodes[n2] = new Node(n2);
				}
				nodes[n1].out.add(n2);
				nodes[n2].in.add(n1);

				// each new line is a new arc
				arcs.addArc(new Arc(nodes[n1], nodes[n2], w1));
				break;
			}
		}
		return new Graph(nodes, nodeCount, arcs, arcCount);
	}

	public static void applySSFile(File f, Graph g) throws FileNotFoundException{
		Scanner s = new Scanner(f);
		while (s.hasNextLine()){
			String line = s.nextLine();
			//components of the line
			String[] comps = line.split(" ");
			switch(comps[0]){
			case "c":
				break;
			case "p":
				break;
			case "s":
				//source problem
				g.sources.add(Integer.decode(comps[1]));
			}
		}
		System.out.println("Applicaiton of .ss file complete.");
	}
	
	public static void main(String[] args0) throws FileNotFoundException, NoSuchFieldException {
		Graph g = GraphGenerator
				.generateGraph(new File("C:\\EclipseWorkspaces\\csse230\\Combinatorics\\src\\Graphs\\NewYork"));
		System.out.println(g.toString());
//		GraphResult res = GraphSolver.GenericAlgorithm(g, 132000);
		GraphResult res = GraphSolver.ModifiedNodeListAlgorithm(g, 132000, HashedLinkedList.STACK);
		
//		g.printResults();
		res.getResults();
	}
}
