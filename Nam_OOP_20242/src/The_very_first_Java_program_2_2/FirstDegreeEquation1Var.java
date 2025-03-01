package The_very_first_Java_program_2_2;

import javax.swing.*;

public class FirstDegreeEquation1Var {
    public static void main(String[] args) {
        double a=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the first coefficient: ", "Input the first coefficient", JOptionPane.INFORMATION_MESSAGE));
        double b=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the second coefficient: ", "Input the second coefficient", JOptionPane.INFORMATION_MESSAGE));
        if(a==0&&b!=0) JOptionPane.showMessageDialog(null, "The equation bears no solution");
        else if(a==0&&b==0) JOptionPane.showMessageDialog(null, "The equation has infinitely many solutions");
        else {
            double sol=-b/a;
            JOptionPane.showMessageDialog(null, "The solution of the equation is: " + sol);
        }
        System.exit(0);
    }
}