package hello1;

class Employee {
    private String employeeName;
    final private double basicSalary;
    private double salaryCoefficient;
    final double SALARY_MAX=1000;

    public Employee(String name, double salary, double coefficient) {
        employeeName=name; basicSalary=salary; salaryCoefficient=coefficient;
    }

    public void printInfo() {
        System.out.printf("Name: %s\nBasic salary: %f\nSalary coefficient: %f\n",employeeName, basicSalary, salaryCoefficient);
    }
    public double calculateSalary() {
        return basicSalary*salaryCoefficient;
    }
    public boolean increaseSalary(double amount) {
        salaryCoefficient+=amount;
        if(calculateSalary()>SALARY_MAX) {
            salaryCoefficient-=amount;
            return false;
        }
        else return true;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getSalaryCoefficient() {
        return salaryCoefficient;
    }
}

public class TestEmployee {
    public static void main(String[] args) {
        Employee employee1=new Employee("Andrew", 200, 1.2);
        Employee employee2=new Employee("Benjamin", 100, 2);
        employee1.printInfo();
        System.out.println(employee1.calculateSalary());
        if(employee1.increaseSalary(0.3)) employee1.printInfo();
    }
}