package hello1;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

interface Animal {
    void makeSound();
}

interface Creature {
    void eat();
}

class Human implements Animal, Creature {
    public void makeSound() {
        System.out.println("Eiyo");
    }
    public void eat() {
        System.out.println("Eat");
    }
}

interface A {
    void show();
}

interface B extends A {
    default void show() {
        System.out.println("Interface B");
    }
}

interface C extends A {
    default void show() {
        System.out.println("Interface C");
    }
}

class D implements B, C {
    public void show() {
        System.out.println("Class D resolves conflict");
        B.super.show();
    }
}

class E extends D implements B, C {
    
}

class BankAccount {
    private String owner;
    public void setOwner(String owner) {
        this.owner=owner;
    }
    public BankAccount(String name) {
        owner=name;
    }
    public BankAccount() {
        this("noname");
    }
}

class Point {
    private double x;
    private double y;
    public Point() {}
    public Point(double x, double y) {
        this.x=x; this.y=y;
    }
    public void setX(double x) {this.x=x;}
    public void setY(double y) {this.y=y;}
    public void printPoint() {
        System.out.println("X: " + x + " Y: " + y);
    }
}

class Student {
    private String name;
    private int year;
    static String school;
    public final String NATION;
    Student(String name, int year) {
        NATION="Vietnam";
        this.name=name; this.year=year;
    }
    Student() {
        this("No Name", 18);
    }
    {
        System.out.println("Student class is called");
    }
    public int getYear() {return year;}
    public void setYear(int year) {this.year=year;}
    public String getSchool() {return school;}
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
}

class Teacher {
    private String name;
    List<Student> students;
    Teacher(String name) {
        this.name = name;
        students = new ArrayList<>();
    }
    void addStudent(Student student) {
        students.add(student);
    }
    void showStudents() {
        System.out.println("Teacher: " + name);
        for(Student s: students) System.out.println("- " + s.getName());
    }
}

//class Integer {
//    int i;
//    Integer(int i) {this.i=i;}
//}

class Room {
    private String type;
    Room(String type) {this.type=type;}
    void showType() {System.out.println(type);}
}

class House {
    private Room room;  //composition
    House() {this.room=new Room("Bedroom");}
    void showHouseDetails() {room.showType();}
}

class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Train {
    private Engine engine;  //aggregation
    Train(Engine engine) {this.engine=engine;}
    void startTrain() {
        engine.start();
        System.out.println("Train is running");
    }
}

public class Practice {
    static void change(int[] arr) {
        arr[2]=4;
    }
    static void printNum(int ...num) {
        for(int i: num) System.out.print(i + " ");
    }
    public static void change(Student std) {
        std=new Student("Hung", 1995);
    }
    public void increase(Student student) {
        student.setName("ABC");
    }
    public static int sum(int... a) {
        int res=0;
        for(int i: a) res+=i;
        return res;
    }
    public static void main(String[] args) {
        Human myHuman=new Human();
        myHuman.makeSound();
        myHuman.eat();
        /*Scanner scanner=new Scanner(System.in);
        System.out.println("Enter username, age, salary: ");
        String username=scanner.next();
        int age=scanner.nextInt();
        double salary=scanner.nextDouble();
        scanner.close();
        System.out.printf("%s %d %f", username, age, salary);*/
        LocalDateTime time= LocalDateTime.now();
        System.out.println(time);
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate=time.format(timeFormatter);
        System.out.println(formattedDate);
        JOptionPane.showMessageDialog(null, "Ban da thao tac loi", "Thong bao loi", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showConfirmDialog(null, "Ban co chac chan muon thoat?", "Hay lua chon", JOptionPane.YES_NO_OPTION);
        Object[] options={"OK", "Cancel"};
        JOptionPane.showOptionDialog(null, "Nhan OK de tiep tuc", "Canh bao", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        int[] arr={1, 2 ,3};
        Practice.change(arr);
        for(int i: arr) System.out.printf("%d ", i);
        Practice.printNum(1, 2, 3, 4);
        Student std=new Student("Nam", 1990);
        System.out.println(std.getYear());
        change(std);
        System.out.println(std.getYear());
        System.gc();
//        Integer n1=47;
//        Integer n2=47;
//        System.out.println(n1==n2);
//        System.out.println(n1.equals(n2));
        House house=new House();
        house.showHouseDetails();
        Float objF=4.67f;   //autoboxing
        float f=objF;       //unboxing
        f=objF.floatValue();    //convert object to float type
        int i=objF.intValue();  //convert object to int type
        i=Integer.parseInt("123");  //convert string to int type
        double d=Double.parseDouble("1.23");    //convert string to double type
        objF=Float.valueOf("4.67");
        String num=objF.toString();
        System.out.println("answer="+1+2+3);
        System.out.println("answer="+(1+2+3));
        StringBuffer sb=new StringBuffer("Java");
        sb.append(" Programming");
        sb.insert(5, "is ");
        sb.replace(8, 19, "fun");
        sb.delete(5, 8);
        sb.reverse();
        System.out.println(sb);
        double x=Math.exp(Math.sqrt(2*Math.PI));
        Student std1=new Student();
        Student std2=new Student();
        std1.school="HUST";
        System.out.println(std2.getSchool());
        Practice practice=new Practice();
        practice.increase(std1);
        System.out.println(std1.getName());
        System.out.println(sum(1, 2, 3, 4));
    }
}
