import javax.swing.JOptionPane;

public class InvalidClassNameException extends Exception {

	public InvalidClassNameException() {
		JOptionPane.showMessageDialog(null, "You Must Enter A Valid Class Name");
	}

}
