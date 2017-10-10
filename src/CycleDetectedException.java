import javax.swing.JOptionPane;

public class CycleDetectedException extends Exception {

   public CycleDetectedException(){
        JOptionPane.showMessageDialog(null, "Circular Dependency Cycle Detected ");
   }

}
