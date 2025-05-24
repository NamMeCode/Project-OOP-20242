package hello2;

import java.util.Random;
import java.util.Scanner;

class OuterClass {
    int x=10;
    static int t=20;
    static class InnerClass {
        int y=5;
        void check(OuterClass obj) {
            System.out.println(obj.x); //error, unable to access non-static member outside class
            System.out.println(y);
        }
    }
    class InnerClass2 {
        int z=15;
    }
}

class MathHelper {
    private MathHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    public static int max(int a, int b) {
        return a>b?a:b;
    }
    public static boolean isEven(int number) {
        return number%2==0;
    }
}

class Outer {
    private class Inner {
        void display() {
            System.out.println("Hello from inner class");
        }
    }
    public void showInnerMessage() {
        Inner inner=new Inner();
        inner.display();
    }
}

interface Payment {
    void processPayment();
}

class CreditCardPayment implements Payment {
    public void processPayment() {
        System.out.println("Processing Credit Card Payment");
    }
}

class PaymentProcessor {
    private Payment payment;

    PaymentProcessor(Payment payment) {
        this.payment = payment;
    }

    void makePayment() {
        payment.processPayment();
    }
}

class Point {
    private int x, y;
    public Point() {}
    public Point(int x, int y) {
        this.x=x; this.y=y;
    }
    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }
}

class Quadrangle {
    protected Point[] corners=new Point[4];
    public Quadrangle(Point p1, Point p2, Point p3, Point p4) {
        corners[0]=p1; corners[1]=p2; corners[2]=p3; corners[3]=p4;
    }
    public Quadrangle() {
        corners[0]=new Point(); corners[1]=new Point(0,1);
        corners[2]=new Point(1, 1); corners[3]=new Point(1,0);
    }
    public void print() {
        corners[0].print(); corners[1].print(); corners[2].print(); corners[3].print();
        System.out.println();
    }
}

class Square extends Quadrangle {
    public Square() {
        corners[0]=new Point(0,0); corners[1]=new Point(0, 1);
        corners[2]=new Point(1, 0); corners[3]=new Point(1, 1);
    }
}

class Player {
    private String name;
    public void setName(String name) {
        this.name=name;
    }
    public String getName() {return name;}
}

class Dice {
    public int thrown() {
        int value;
        Random random=new Random();
        value=random.nextInt(6)+1;
        return value;
    }
}

class Arbitrator {
    private String name;
    private String winner;
    private int bestPoint;
    public void setName(String name) {
        this.name=name;
    }
    public void getBestPoint(int point, String name) {
        if(bestPoint<point) {
            bestPoint=point; winner=name;
        }
    }
    public void printWinner() {
        System.out.println("The winner is: " + winner);
        bestPoint=0;
    }
}

class Game {
    private Player[] players=new Player[4];
    private Dice dice=new Dice();
    private Arbitrator arbitrator=new Arbitrator();
    private Scanner scanner=new Scanner(System.in);
    public void setupGame() {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Please enter names of 4 players: ");
        for(int i=0; i<4; i++) {
            players[i]=new Player();
            players[i].setName(scanner.next());
        }
        System.out.println();
        System.out.print("Please enter name of the arbitrator: ");
        arbitrator.setName(scanner.next());
        System.out.println();
    }
    public void play() {
        System.out.println("Start game");
        do {
            for(int i=0; i<4; i++) {
                int value=dice.thrown();
                String name=players[i].getName();
                System.out.print("Roll the dice, " + name + " gets " + value + "\n");
                arbitrator.getBestPoint(value, name);
            }
            arbitrator.printWinner();
            System.out.print("Do you want to play again? (Yes, No) ");
            if(scanner.next().equalsIgnoreCase("No")) break;
        } while(true);
    }
    public void closeScanner() {
        scanner.close();
    }
}

public class Main {
    int x;
    Main() {
        x=10;
    }
    public static void main(String[] args) {
        System.out.println("Hello");
        OuterClass outerObj=new OuterClass();
        OuterClass.InnerClass innerObj=new OuterClass.InnerClass();
        innerObj.check(outerObj);
        OuterClass.InnerClass2 innerObj2=outerObj.new InnerClass2();
        MathHelper obj; //=new MathHelper(); error: instantiate object of utility class
        int max=MathHelper.max(1, 2);
        Outer objOuter=new Outer();
        objOuter.showInnerMessage();
        Game game=new Game();
        game.setupGame();
        game.play();
        game.closeScanner();
        Square square=new Square();
        square.print();
        Payment payment = new CreditCardPayment();
        PaymentProcessor processor = new PaymentProcessor(payment);
        processor.makePayment();  // Output: Processing Credit Card Payment
    }
}
