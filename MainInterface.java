import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MainInterface {
	
	public static void main(String[] args) throws FileNotFoundException{
		Scanner s = new Scanner(System.in);
		HashMap<String, Graph> map = new HashMap<>();
		
		System.out.print("Enter the filepath of desired directory.\nMake sure to include final \\ on end of directory name.\n-->");
		
		String filepath = s.nextLine();
		String file;
		String graphName;
		Graph g = null;
		//determines length of operation of loops
		Boolean outer = true;
		Boolean inner = true;
		while (outer){
			System.out.print("Enter the name of the desired file\n-->");
			file = s.nextLine();
			try {
				System.out.println("Creating graph...");
				g = GraphGenerator.generateGraph(new File(filepath + file));
			} catch (FileNotFoundException exception) {
				System.out.println("File not found...");
				continue;
			} catch (NoSuchFieldException exception) {
				// TODO Auto-generated catch-block stub.
				exception.printStackTrace();
			}
			System.out.print("Enter desired name for this graph\n-->");
			graphName = s.nextLine();
			map.put(graphName, g);
			inner = true;
			
			// main command loop
			while (inner){
				System.out.print("Enter a command\n-->");
				String line = s.nextLine();
				String[] comps = line.split(" ");
				switch(comps[0]){
				case "ss":
					if (comps.length!=4){
						System.out.println("Incorrect amount of arguments.");
						break;
					}
					String graphRef = comps[1];
					Graph graphObj = map.get(graphRef);
					if (graphObj==null){
						System.out.println("No graph by that name exists.");
						break;
					}
					//else graph exists
					String ssFile = comps[2];
					int algNum = Integer.decode(comps[3]);
					//setup graphObj sources array
					GraphGenerator.applySSFile(new File(filepath+ssFile), graphObj);
					System.out.println();
					for (int source : graphObj.sources){
						System.out.println(String.format("Calculating shortest paths for source %d", source));
						switch (algNum){
						case 1:
							graphObj.results.add(GraphSolver.GenericAlgorithm(graphObj, source));
							break;
						case 2:
							graphObj.results.add(GraphSolver.ModifiedNodeListAlgorithm(graphObj, source, HashedLinkedList.FIFO));
							break;
						case 3:
							graphObj.results.add(GraphSolver.ModifiedNodeListAlgorithm(graphObj, source, HashedLinkedList.STACK));
							break;
						case 4:
							graphObj.results.add(GraphSolver.ModifiedNodeListAlgorithm(graphObj, source, HashedLinkedList.DEQUEUE));
							break;
						case 5:
							graphObj.results.add(GraphSolver.ModifiedNodeListAlgorithm(graphObj, source, HashedLinkedList.PRIORITY));
							break;
						default:
							System.out.println("Not a proper algorithm number.");
							break;
						}
					}
					System.out.println("Computations complete.\n");	
					break;
				case "res":
					System.out.println();
					graphRef = comps[1];
					int n = Integer.decode(comps[2]);
					GraphResult gr = map.get(graphRef).results.get(n);
					gr.getResults(); //print to console
					System.out.println();
					break;
				case "print":
					System.out.println("All available graphs:\n");
					System.out.println(map.keySet().toString());
					System.out.println();
					break;
				case "ng":
					inner = false;
					break;
				case "exit":
					System.out.println("Exiting...");
					outer = false;
					inner = false;
					break;
				default:
					System.out.println("Not a valid command.");
					break;
					
					
				}
				
			}
				
			
		}
		
	}
}
