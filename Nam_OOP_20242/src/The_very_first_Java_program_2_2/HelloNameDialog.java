package The_very_first_Java_program_2_2;

import javax.swing.JOptionPane;
public class HelloNameDialog {
    public static void main(String[] args) {
        String result=JOptionPane.showInputDialog("Please enter your name: ");
        JOptionPane.showMessageDialog(null, "Hi " + result + "!");
        System.exit(0);
    }
}
