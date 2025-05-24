package hello2;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hello1.Main;

class Person {
    private String name="Sung Jinwoo";
    private LocalDate birthday;
    public Person() {}
    public Person(String name) {
        this.name=name;
    }
    //Getter
    String getName() {
        return name;
    }
    //Setter
    void setName(String newName) {
        this.name=newName;
    }
    LocalDate getBirthday() {return birthday;}
    void setBirthday(String date) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        birthday=LocalDate.parse(date);
    }
}

class Employee extends Person {
    private double salary;
    public Employee() {}
    public Employee(String name, double salary) {
        super(name); this.salary=salary;
    }
    public void setSalary(double salary) {
        this.salary=salary;
    }
    public String getDetail() {
        return this.getName() + "," + this.getBirthday() + "," + salary;
    }
}

class Animal {
    void animalSound() {
        System.out.println("The animal makes the sound");
    }
}

class Pig extends Animal {
    @Override
    void animalSound() {
        System.out.println("The pig says: wee wee");
    }
}

class Dog extends Animal {
    @Override
    void animalSound() {
        System.out.println("The dog says: bow bow");
    }
    void jump() {
        System.out.println("The dog jump");
    }
}

class Shape {
    protected String name;
    Shape(String n) {name=n;}
    public String getName() {return name;}
    public float calculateArea() {return 0.0f;}
    private void printName() {
        System.out.println("name");
    }
}

class Circle extends Shape {
    private int radius;
    Circle(String n, int r) {
        super(n);
        radius=r;
    }
    public float calculateArea() {
        return 3.14f*radius*radius;
    }
}

class Triangle extends Shape {
    private int base, height;
    Triangle(String n, int b, int h) {
        super(n);
        base=b; height=h;
    }
    public float calculateArea() {
        return 0.5f*base*height;
    }
}

public class Second {
    public static void main(String[] args) {
        hello2.Main myObj=new hello2.Main();
        System.out.println(myObj.x);
        Person obj=new Person();
        String name;
        name=obj.getName();
        name="Hello";
        obj.setName(name);
        Animal myAnimal=new Animal();
        Animal myDog=new Dog();
        Animal myPig=new Pig();
        myAnimal.animalSound();
        myDog.animalSound();
        myPig.animalSound();
        ((Dog)myDog).jump();
        Person p;
        Employee e=new Employee();
        p=e;
        p.setName("Nam");
    }
}
