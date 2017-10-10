/**
 * FileName: CycleDetectedException.java 
 * Author: Rebecca Johnson 
 * Date: 10/10/2017
 * Description: This exception will be thrown if there is a cycle.
 *
 */

import javax.swing.JOptionPane;

public class CycleDetectedException extends Exception {

   public CycleDetectedException(){
        JOptionPane.showMessageDialog(null, "Circular Dependency Cycle Detected ");
   }

}
