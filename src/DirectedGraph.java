
/**
 * FileName: DirectedGraph.java 
 * Author: Rebecca Johnson 
 * Date: 10/10/2017
 * Description: This class build the graph and generates the results
 * 		using a depth-first search and presents the answer in topological order.
 *
 */

import java.io.*;
import java.util.*;

public class DirectedGraph {

	// to hold the output in the correct order
	private String outputOrder;

	// to hold the vertices index
	private ArrayList<Integer>[] verticesIndex;

	// to hold the map of the class name and index
	private HashMap<String, Integer> vertexMap = new HashMap<>();

	// to hold the list of valid classes from the file
	private ArrayList<String> validClasses = new ArrayList<String>();

	// to determine whether the vertex is discovered
	private boolean isDiscovered[];

	// to determine whether the vertex is finished
	private boolean isFinished[];

	// to hold the stack of classes in reverse topological order
	private Stack<Integer> stack;

	// to hold the number of classes in the file
	private int numberOfClasses = 0;

	// this method initializes the graph
	public boolean initializeGraph(String file) {

		try {

			// scanner object to read the file
			Scanner inputFile = new Scanner(new File(file));

			// used to count the number of class names
			int count = 0;

			// will execute as long as there is more data in the file
			while (inputFile.hasNext()) {

				// to hold the line while taking out any extra spaces
				String line = inputFile.nextLine().replaceAll("\\s+", " ");

				// hold the array of classes of each line
				String[] classNames = line.split(" ");

				// will execute as long as there are elements in the classNames
				// array
				for (int i = 0; i < classNames.length; i++) {

					// checks if the current class name already exists
					if (vertexMap.containsKey(classNames[i]))
						vertexMap.put(classNames[i], vertexMap.get(classNames[i]));

					// otherwise, stores the class name and vertex in new index
					// in the hash map
					else {
						vertexMap.put(classNames[i], count);
						validClasses.add(classNames[i]);
						count++;
					}

				}

			}

			// gets the number of classes
			numberOfClasses = vertexMap.size();

			// initializes the list of vertices
			verticesIndex = new ArrayList[numberOfClasses];

			// initializes boolean variables
			isDiscovered = new boolean[numberOfClasses];
			isFinished = new boolean[numberOfClasses];

			// rescans file
			inputFile = new Scanner(new File(file));

			// will execute as long as there is more data in the file
			while (inputFile.hasNext()) {

				// to hold the line while taking out any extra spaces
				String line = inputFile.nextLine().replaceAll("\\s+", " ");

				// hold the array of classes of each line
				String[] classNames = line.split("[ \n]");

				// associates the verticesIndex list with the hash map
				verticesIndex[vertexMap.get(classNames[0])] = new ArrayList<>();

				// associates the verticesIndex list with the hash map
				for (int i = 1; i < classNames.length; i++)
					verticesIndex[vertexMap.get(classNames[0])].add(vertexMap.get(classNames[i]));

			}

			// closes file
			inputFile.close();

			return true;

		} catch (FileNotFoundException ex) {

			return false;
		}
	}

	// this method retrieves the result in topological order
	public String generateTopologicalOrder(String key) {

		// resets the output result
		outputOrder = "";

		// initializes boolean variables
		isDiscovered = new boolean[numberOfClasses];
		isFinished = new boolean[numberOfClasses];

		// initializes the stack
		stack = new Stack<>();

		try {

			// calls depthFirstSearch method and sends over desired class
			depthFirstSearch(vertexMap.get(key));

		} catch (CycleDetectedException e) {

			e.printStackTrace();
		}

		// will execute as long as the stack is not empty
		while (!stack.empty()) {

			// gets the index from the stack and removes it from the stack
			int index = stack.pop();

			// initializes outputOrder
			outputOrder = outputOrder + " " + validClasses.get(index);
		}

		return outputOrder;

	}

	// this method performs the depth-first search
	private void depthFirstSearch(int vertex) throws CycleDetectedException {

		// checks if the vertex is discovered
		if (isDiscovered[vertex])

			// throws a new cycle detected exception
			throw new CycleDetectedException();

		// checks if the vertex has finished
		else if (isFinished[vertex])
			return;

		// sets the vertex as discovered
		isDiscovered[vertex] = true;

		// checks if the vertex is null
		if (verticesIndex[vertex] != null)

			// goes through each vertex and sends new one back using recursion
			for (Integer v : verticesIndex[vertex])
				depthFirstSearch(v);

		// sets the vertex as finished
		isFinished[vertex] = true;

		// pushes the vertex to the stack
		stack.push(vertex);
	}

	// returns a list of valid classes from within the file
	public ArrayList<String> getValidClasses() {
		return validClasses;
	}

}