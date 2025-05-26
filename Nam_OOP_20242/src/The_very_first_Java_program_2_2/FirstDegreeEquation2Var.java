package The_very_first_Java_program_2_2;

import javax.swing.*;

public class FirstDegreeEquation2Var {
    public static void main(String[] args) {
        double a11=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the first coefficient of the first equation: "));
        double a12=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the second coefficient of the first equation: "));
        double b1=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the third coefficient of the first equation: "));
        double a21=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the first coefficient of the second equation: "));
        double a22=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the second coefficient of the second equation: "));
        double b2=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the third coefficient of the second equation: "));
        double det1, det2, det3;
        det1=a11*a22-a21*a12; det2=b1*a22-b2*a12; det3=a11*b2-a21*b1;
        if(det1!=0) {
            double sol1, sol2;
            sol1=det2/det1; sol2=det3/det1;
            JOptionPane.showMessageDialog(null, "The first solution is: " + sol1);
            JOptionPane.showMessageDialog(null, "The second solution is: " + sol2);
        }
        else if(det2==0&&det3==0) JOptionPane.showMessageDialog(null, "The system has infinite solution");
        else JOptionPane.showMessageDialog(null, "The system has no solution");
        System.exit(0);
    }
}