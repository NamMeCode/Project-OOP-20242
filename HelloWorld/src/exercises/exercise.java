package exercises;
import java.util.Scanner;

public class exercise {
    public static void main(String[] args) {
        /*Scanner scanner=new Scanner(System.in);
        int n;
        do {
            n=scanner.nextInt();
        } while(n<3||n>8);
        int[][] matrix=new int[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                matrix[i][j]=scanner.nextInt();
            }
        }
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }*/
        Employee e1=new Employee("David", 100);
        Employee e2=new Employee("Ricardo", 200);
        Employee e3=new Employee("Roger", 300);
        Employee.getEmployeeNum();
        System.out.println("The total salary is: " + Employee.calculateSalary(e1, e2, e3));
    }
}

class BankAccount {
    private String owner;
    private double balance;

    public boolean debit(double amount) {
        if(amount<=balance) {
            balance-=amount;
            return true;
        }
        else {
            return false;
        }
    }
    public void credit(double amount) {
        balance+=amount;
    }
}

class Employee {
    private String name;
    private double salary;
    private static int objNum;
    public Employee(String name, double salary) {
        this.name=name; this.salary=salary;
        objNum+=1;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public static void getEmployeeNum() {
        System.out.println("The number of employees is: " + objNum);
    }

    public static double calculateSalary(Employee... employees) {
        double res=0;
        for(Employee i: employees) {
            res+=i.salary;
        }
        return res;
    }
}