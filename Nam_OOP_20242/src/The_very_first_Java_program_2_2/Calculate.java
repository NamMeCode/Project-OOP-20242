package The_very_first_Java_program_2_2;

import javax.swing.*;

public class Calculate {
    public static void main(String[] args) {
        double num1, num2;
        num1=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the first number: ", "Input the first number", JOptionPane.INFORMATION_MESSAGE));
        num2=Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter the second number: ", "Input the second number", JOptionPane.INFORMATION_MESSAGE));
        double sum, difference, product, quotient;
        sum=num1+num2; difference=num1-num2; product=num1*num2; quotient=num1/num2;
        JOptionPane.showMessageDialog(null, "The sum of two numbers is: " + sum, "The sum", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "The difference of two numbers is: " + difference, "The difference", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "The product of two numbers is: " + product, "The product", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "The quotient of two numbers is: " + quotient, "The quotient", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
