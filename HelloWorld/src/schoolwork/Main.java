package schoolwork;

class Person {

}

class Employee extends Person {

}

class Manager extends Employee {

}

public class Main {
    public static void main(String[] args) {
        Employee e=new Employee();
        Person p=e;
        Employee ee=(Employee) p;
        //Manager m=(Manager) ee; this will throw a runtime error
        //because ee is Employee, not necessarily Manager
        Person p2=new Manager();
        Employee e2=(Employee) p2;
        Person p3=new Employee();
        Manager e3=(Manager) p3;
    }
}
