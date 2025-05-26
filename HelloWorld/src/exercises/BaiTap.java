package exercises;
import java.util.*;

class HelloWorld {
    static void printHelloWorld() {
        System.out.println("Hello World");
    }
}

class Calculator {
    int add(int a, int b) {return a+b;}
    double add(double a, int b) {return a+b;}
    double add(int a, double b) {return a+b;}
    double add(double a, double b) {return a+b;}
    int subtract(int a, int b) {return a-b;}
    double subtract(double a, int b) {return a-b;}
    double subtract(int a, double b) {return a-b;}
    double subtract(double a, double b) {return a-b;}
    int multiply(int a, int b) {return a*b;}
    double multiply(double a, int b) {return a*b;}
    double multiply(int a, double b) {return a*b;}
    double multiply(double a, double b) {return a*b;}
    double divide(int a, int b) {return a/(double)b;}
    double divide(double a, int b) {return a/b;}
    double divide(int a, double b) {return a/b;}
    double divide(double a, double b) {return a/b;}
}

class CheckEvenOdd {
    static boolean check(int a) {return a%2==0;}
}

class Person {
    String name;
    int birthday;
    Employees e;

    public void setEmployees(Employees e) {
        this.e=e;
    }
    public void setName() {}
    public void setBirthday() {}
}

class Employees extends Person {
    float salary;
    Person p;

    public void setPerson(Person p) {
        this.p=p;
    }
    public void setSalary() {}
    public void getDetail() {}
}

class Manager extends Employees {

}

public class BaiTap {
    public static void main(String[] args) {
        HelloWorld.printHelloWorld();
        Calculator cal=new Calculator();
        System.out.println(cal.add(1, 2));
        System.out.println(cal.add(3.5, 6.9));
        Scanner scanner=new Scanner(System.in);
        int x=scanner.nextInt();
        if(CheckEvenOdd.check(x)) System.out.println("The input number is even");
        else System.out.println("The input number is odd");
        String str="Hello";
        String str2="Hello";
        String s="World";
        char[] c={'W', 'o', 'r', 'l', 'd'};
        System.out.println(str==s);
        System.out.println(str==str2);
        System.out.println(str.equals(c));
        System.out.println(str.compareTo(s));
        System.out.println(str.charAt(3));
        HelloWorld obj=new HelloWorld();
        int a=5;
        float b=(float) a;

        Person p=new Person();
        Employees e1=(Employees) p;
        Employees e=new Employees();
        e1.setPerson(e);
        p.setEmployees((Employees) p);
        Manager m=(Manager) e1;
        PriorityQueue<Integer> v;
    }
}