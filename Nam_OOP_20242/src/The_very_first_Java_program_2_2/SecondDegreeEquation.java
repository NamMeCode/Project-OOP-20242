package The_very_first_Java_program_2_2;
import javax.swing.*;

public class SecondDegreeEquation {
    public static void main(String[] args) {
        double a=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the first coefficient: "));
        double b=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the second coefficient: "));
        double c=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the third coefficient: "));
        double delta=b*b-4*a*c;
        if(delta<0) JOptionPane.showMessageDialog(null, "The equation has no solution");
        else if(delta==0) {
            double sol=-b/(2*a);
            JOptionPane.showMessageDialog(null, "The only solution is: " + sol);
        }
        else {
            double sol1, sol2;
            sol1=(-b-Math.sqrt(delta))/(2*a);
            sol2=(-b+Math.sqrt(delta))/(2*a);
            JOptionPane.showMessageDialog(null, "The first solution is: " + sol1);
            JOptionPane.showMessageDialog(null, "The second solution is: " + sol2);
        }
        System.exit(0);
    }
}
