package hust.soict.ict.lab01;
import javax.swing.JOptionPane;

public class DaysInMonth {
    static boolean checkDays(int year, int month) {
        int day=0;
        switch(month) {
            case 1, 3, 5, 7, 8, 10, 12: day=31; break;
            case 4, 6, 9, 11: day=30; break;
            case 2:
                if((year%4==0&&year%100!=0)||(year%400==0)) day=29;
                else day=28;
                break;
        }
        JOptionPane.showMessageDialog(null, "The number of days in the month is " + day);
        return true;
    }
    static boolean threeLetterCase(int year, String str) {
        int month=0;
        switch(str) {
            case "Jan": month=1; break;
            case "Feb": month=2; break;
            case "Mar": month=3; break;
            case "Apr": month=4; break;
            case "May": month=5; break;
            case "Jun": month=6; break;
            case "Jul": month=7; break;
            case "Aug": month=8; break;
            case "Sep": month=9; break;
            case "Oct": month=10; break;
            case "Nov": month=11; break;
            case "Dec": month=12; break;
            default: JOptionPane.showMessageDialog(null, "Invalid input"); break;
        }
        if(month!=0) return DaysInMonth.checkDays(year, month);
        else return false;
    }
    static boolean abbreviationCase(int year, String str) {
        int month=0;
        switch(str) {
            case "Jan.": month=1; break;
            case "Feb.": month=2; break;
            case "Mar.": month=3; break;
            case "Apr.": month=4; break;
            case "May": month=5; break;
            case "June": month=6; break;
            case "July": month=7; break;
            case "Aug.": month=8; break;
            case "Sept.": month=9; break;
            case "Oct.": month=10; break;
            case "Nov.": month=11; break;
            case "Dec.": month=12; break;
            default: JOptionPane.showMessageDialog(null, "Invalid input"); break;
        }
        if(month!=0) return DaysInMonth.checkDays(year, month);
        else return false;
    }
    static boolean fullNameCase(int year, String str) {
        int month=0;
        switch(str) {
            case "January": month=1; break;
            case "February": month=2; break;
            case "March": month=3; break;
            case "April": month=4; break;
            case "May": month=5; break;
            case "June": month=6; break;
            case "July": month=7; break;
            case "August": month=8; break;
            case "September": month=9; break;
            case "October": month=10; break;
            case "November": month=11; break;
            case "December": month=12; break;
            default: JOptionPane.showMessageDialog(null, "Invalid input"); break;
        }
        if(month!=0) return DaysInMonth.checkDays(year, month);
        else return false;
    }
    public static void main(String[] args) {
        while(true) {
            boolean valid=true;
            String str1=JOptionPane.showInputDialog(null, "Input a month");
            String str2=JOptionPane.showInputDialog(null, "Input a year");
            if(str2.length()!=4) {
                JOptionPane.showMessageDialog(null, "Invalid input");
                valid=false;
            }
            else {
                int year;
                boolean isDigit=true;
                for(char c: str2.toCharArray()) {
                    if(!Character.isDigit(c)) {
                        isDigit=false;
                        break;
                    }
                }
                if(!isDigit) {
                    JOptionPane.showMessageDialog(null, "Invalid input");
                    valid=false;
                }
                else {
                    year = Integer.parseInt(str2);
                    if (str1.length() <= 2) {
                        for (char c : str1.toCharArray()) {
                            if (Character.isAlphabetic(c)) {
                                isDigit = false;
                                break;
                            }
                        }
                        if (isDigit) {
                            int month = Integer.parseInt(str1);
                            if (month <= 12 && month >= 1) valid= DaysInMonth.checkDays(year, month);
                            else {
                                JOptionPane.showMessageDialog(null, "Invalid input");
                                valid=false;
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid input");
                            valid=false;
                        }
                    }
                    else if (str1.length() == 3) valid= DaysInMonth.threeLetterCase(year, str1);
                    else if (str1.length() <= 5) valid= DaysInMonth.abbreviationCase(year, str1);
                    else valid= DaysInMonth.fullNameCase(year, str1);
                }
            }
            if(valid) break;
        }
    }
}