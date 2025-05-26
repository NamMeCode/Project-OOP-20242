package hello1;

//import hello2.Second;
import java.util.*;
import hello2.Vehicle;

class MyCar extends Vehicle {
    void thisMethod() {
        MyCar thisCar=new MyCar();
        thisCar.brand="Toyota";
        //year=6; cant inherit because attribute year is declared as default
        //and MyCar and Vehicle are in different packages
    }
}

class Car {
    String brand="Toyota";
    String model;
    String color;
    int batch;
    private double price;
    static int count_car=0;
    Car(String brand, String model, String color, int batch, double price) {
        this.brand=brand; this.model=model; this.color=color; this.batch=batch; this.price=price;
        count_car++;
    }
    void go() {
        System.out.println("Go");
    }
    static void stop() {
        System.out.println("Stop");
    }
    public void showSpeed() {
        System.out.println("The speed of the car is: 50km/h");
    }
}

class Crossover extends Car {
    String brand;
    double price;
    Crossover(String brand, String model, String color, int batch, double price) {
        super(brand, model, color, batch, price);
        System.out.println("Child constructor called");
    }
}

abstract class Bike {
    String biker;
    int age;
    static void show(){
        System.out.println("This bike is mine");
    }
    abstract void display();
    void showBrand() {
        System.out.println("This brand is of type VIP");
    }
}

class Wheel extends Bike {
    void display() {
        System.out.println("This is an abstract method");
    }
    void rotate() {
        System.out.println("The wheel is rotating");
    }
}

final class Motorbike {
    String brand;
    double price;
    void show(){
        System.out.println("This motorbike is mine");
    }
}

/**
 * @author Nam
 * This is the example
 * of java doc comment.
 * The java doc describes the class below it.
 */
public class Main {
    public int x;
    final int y=7;
    int z=6;
    public Main() {
        x=5;
    }
    public static String myMethod(String name) {
        System.out.println("I just got executed " + name);
        return name;
    }
    static int plusMethod(int a, int b) {
        return a + b;
    }
    static double plusMethod(double a, double b) {
        return a + b;
    }
    public static void main(String[] args) {
        String[] cars={"Honda","Audi","BMW"};
        int[] myNum={1,2,3};
        System.out.println(cars[0]);
        String fname=myMethod("Liam");
        int myNum1=plusMethod(myNum[0],myNum[1]);
        double myNum2=plusMethod(4.6, 5.4);
        System.out.printf("%d %f", myNum1, myNum2);
        int $hello=1;
        long longtype=1235346386L;
        int hexa=0x123; int octal=0123;
        System.out.printf("%x %o", hexa, octal);
        /**a comment
         * also a comment
         */
        int[] num=new int[5];
        int[] num2={1, 2, 3, 4};
        char c[]=new char[3];
        System.out.println(num2.hashCode());
        for(int i: num) System.out.println(i);
        Main obj=new Main();
        System.out.println(obj.x);
        Main myObj1=new Main();
        Main myObj2=new Main();
        System.out.printf("%d %d", myObj1.x, myObj2.x);
        myObj1.x=40;
        myObj1.z=50;
        System.out.printf("%d %d", myObj1.x, myObj2.x);
        hello2.Second myObj3=new hello2.Second();
        Car car1=new Car("Toyota", "Vios", "White", 2024, 1440.65);
        car1.brand="Honda";
        car1.count_car++;
        System.out.println(car1.brand);
        car1.go();
        car1.showSpeed();
        Car.stop();
        System.out.println(Car.count_car);
        //Bike bike=new Bike(); error: abstract class cant be used to instantiate
        Bike.show();
        Bike bike=new Wheel(); //polymorphism
        bike.showBrand();
        bike.display();
        //bike.rotate(); error: bike is of Bike class while rotate() is in Wheel class
        bike.biker="Nam";
        Scanner myObj=new Scanner(System.in);
        System.out.println("Enter username: ");
        String username=myObj.nextLine();
        System.out.println("The username is: " + username);
        Vehicle thisCar=new Vehicle();
        MyCar aCar=new MyCar();
        aCar.honk();
        outer: for(int i=0; i<5; i++) {
            inner: for(int j=0; j<6; j++) {
                if(i==2) break;
                if(j==5) break outer;
                System.out.printf("%d %d\n", i, j);
            }
        }
        MyCar[] listCar=new MyCar[5];
        long startTime=System.currentTimeMillis();
        StringBuffer s=new StringBuffer("");
        for(int i=0; i<10000; i++) s.append(i);
        long timeSpent=System.currentTimeMillis()-startTime;
        System.out.println(timeSpent);
        startTime=System.currentTimeMillis();
        String str="";
        for(int i=0; i<10000; i++) str+=i;
        timeSpent=System.currentTimeMillis()-startTime;
        System.out.println(timeSpent);
    }
}