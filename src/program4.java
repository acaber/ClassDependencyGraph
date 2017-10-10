/**
 * FileName: program4.java 
 * Author: Rebecca Johnson 
 * Date: 10/10/2017
 * Description: This class creates the GUI to ask the user to enter
 * 		a valid file name and a valid class that needs to be 
 * 		recompiled and displays the compilation order.
 *
 */

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class program4 extends JFrame {

	private JLabel inputFileLabel;
	private JLabel classToRecompileLabel;
	private static JLabel recompilationOrder;

	private static JTextField inputFileText;
	private static JTextField classToRecompileText;

	private static JButton buildGraphBtn;
	private static JButton topologicalOrderBtn;

	private JPanel inputPanel;
	private JPanel classPanel;
	private JPanel recompilePanel;

	private Container panelGroup;

	static boolean isSelectedInOrder;

	private DirectedGraph graph;

	public program4() {

		super("Class Dependency Graph");

		// input file label specifications
		inputFileLabel = new JLabel("       Input file name:              ");

		// input file text field specifications
		inputFileText = new JTextField();
		inputFileText.setPreferredSize(new Dimension(200, 25));
		inputFileText.setToolTipText("Builds graph from specified text file");

		// class to recompile label specifications
		classToRecompileLabel = new JLabel("Class to recompile:        ");

		// class to recompile text field specifications
		classToRecompileText = new JTextField();
		classToRecompileText.setPreferredSize(new Dimension(200, 25));
		classToRecompileText.setToolTipText("Enter valid class");

		// build directed graph button specifications
		buildGraphBtn = new JButton("Build Directed Graph");

		// topological order button specifications
		topologicalOrderBtn = new JButton("Topological Order");

		// input panel specifications
		inputPanel = new JPanel();

		// class panel specifications
		classPanel = new JPanel();

		// recompile panel specifications
		recompilePanel = new JPanel();
		recompilePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Recompilation Order"));
		recompilePanel.setPreferredSize(new Dimension(560, 175));
		recompilePanel.setBackground(Color.WHITE);

		// recompilation order label specifications
		recompilationOrder = new JLabel("");

		// calls addComponents method to add components to panels
		addComponents();

		// panel group specifications
		panelGroup = new Container();
		panelGroup.setLayout(new BoxLayout(panelGroup, BoxLayout.Y_AXIS));
		panelGroup.add(inputPanel);
		panelGroup.add(classPanel);
		panelGroup.add(recompilePanel);

		// calls setFrame method to set up the frame
		setFrame();

		// build graph button action listener
		buildGraphBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				// holds the name of the file from the input file text field
				String file = inputFileText.getText();

				graph = new DirectedGraph();

				// checks if the file can be opened
				if (graph.initializeGraph(file)) {

					JOptionPane.showMessageDialog(null, "Graph Built Sucessfully");
				} else
					JOptionPane.showMessageDialog(null, "File Did Not Open");

				isSelectedInOrder = true;

			}

		});

		topologicalOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				recompilationOrder.setText("");

				if (isSelectedInOrder) {

					String className = classToRecompileText.getText();

					boolean validClassName = false;

					ArrayList<String> validClasses = graph.getValidClasses();

					for (int i = 0; i < validClasses.size(); i++) {
						if (validClasses.get(i).equals(className))
							validClassName = true;
					}

					if (className.isEmpty() || !validClassName)
						new InvalidClassNameException();

					recompilationOrder.setText(graph.generateTopologicalOrder(className));

				}

				else
					JOptionPane.showMessageDialog(null, "You Must Build The Graph First");
			}

		});

	}

	// this method sets up the JFrame
	public void setFrame() {
		setPreferredSize(new Dimension(580, 280));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(panelGroup);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// this method adds the components to the panels
	public void addComponents() {
		inputPanel.add(inputFileLabel, BorderLayout.CENTER);
		inputPanel.add(inputFileText, BorderLayout.CENTER);
		inputPanel.add(buildGraphBtn, BorderLayout.CENTER);
		classPanel.add(classToRecompileLabel, BorderLayout.CENTER);
		classPanel.add(classToRecompileText, BorderLayout.CENTER);
		classPanel.add(topologicalOrderBtn, BorderLayout.CENTER);
		recompilePanel.add(recompilationOrder, BorderLayout.CENTER);
	}

	// main method
	public static void main(String[] args) {
		new program4();

	}

}