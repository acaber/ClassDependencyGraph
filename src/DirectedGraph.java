import java.io.*;
import java.util.*;

public class DirectedGraph {

	private String outputOrder;
	private ArrayList<Integer>[] adjacentVertices;
	private HashMap<String, Integer> vertexMap = new HashMap<>();
	private ArrayList<String> validClasses = new ArrayList<String>();
	private boolean isDiscovered[];
	private boolean isFinished[];
	private Stack<Integer> stack;
	private int numberOfClasses = 0;

	public boolean initializeGraph(String file) {
		try {

			Scanner inputFile = new Scanner(new File(file));

			int count = 0;

			while (inputFile.hasNext()) {

				String[] classNames = inputFile.nextLine().split(" ");

				for (int i = 0; i < classNames.length; i++) {

					if (vertexMap.containsKey(classNames[i]))
						vertexMap.put(classNames[i], vertexMap.get(classNames[i]));
					else {
						vertexMap.put(classNames[i], count);
						validClasses.add(classNames[i]);
						count++;
					}

				}

				System.out.println(vertexMap);
			}

			System.out.println("here");

			numberOfClasses = vertexMap.size();

			adjacentVertices = new ArrayList[numberOfClasses];
			isDiscovered = new boolean[numberOfClasses];
			isFinished = new boolean[numberOfClasses];

			inputFile = new Scanner(new File(file));

			while (inputFile.hasNext()) {

				String[] classNames = inputFile.nextLine().split("[ \n]");
				adjacentVertices[vertexMap.get(classNames[0])] = new ArrayList<>();

				for (int i = 1; i < classNames.length; i++) {
					adjacentVertices[vertexMap.get(classNames[0])].add(vertexMap.get(classNames[i]));
					System.out.println("className[0]: " + vertexMap.get(classNames[0]));
					System.out.println("className[i]:" + vertexMap.get(classNames[i]));
				}
			}

			inputFile.close();
			return true;

		} catch (FileNotFoundException ex) {

			return false;
		}
	}

	public String generateTopologicalOrder(String key) {

		outputOrder = "";
		isDiscovered = new boolean[numberOfClasses];
		isFinished = new boolean[numberOfClasses];
		stack = new Stack<>();

		try {
			DepthFirstSearch(vertexMap.get(key));
		} catch (CycleDetectedException e) {

			e.printStackTrace();
		}

		System.out.println(stack);

		while (!stack.empty()) {
			int index = stack.pop();
			System.out.println(index);
			System.out.println(validClasses.get(index));
			outputOrder = outputOrder + " " + validClasses.get(index);
		}

		return outputOrder + " ";

	}

	private void DepthFirstSearch(int vertex) throws CycleDetectedException {

		if (isDiscovered[vertex])
			throw new CycleDetectedException();

		else if (isFinished[vertex])
			return;

		isDiscovered[vertex] = true;

		if (adjacentVertices[vertex] != null) {
			for (Integer v : adjacentVertices[vertex])
				DepthFirstSearch(v);
		}

		isFinished[vertex] = true;
		stack.push(vertex);
	}

	public ArrayList<String> getValidClasses() {
		return validClasses;
	}

}