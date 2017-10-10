/**
 * FileName: InvalidClassNameException.java 
 * Author: Rebecca Johnson 
 * Date: 10/10/2017
 * Description: This exception will be thrown if the specified class
 * 		is not located in the file.
 *
 */

import javax.swing.JOptionPane;

public class InvalidClassNameException extends Exception {

	public InvalidClassNameException() {
		JOptionPane.showMessageDialog(null, "You Must Enter A Valid Class Name");
	}

}
